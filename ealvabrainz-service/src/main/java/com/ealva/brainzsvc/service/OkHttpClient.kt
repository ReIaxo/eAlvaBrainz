/*
 * Copyright (c) 2020  Eric A. Snell
 *
 * This file is part of eAlvaBrainz
 *
 * eAlvaBrainz is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 *  the License, or (at your option) any later version.
 *
 * eAlvaBrainz is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with eAlvaBrainz.
 * If not, see <http://www.gnu.org/licenses/>.
 */

@file:Suppress("Indentation")

package com.ealva.brainzsvc.service

import com.burgstaller.okhttp.AuthenticationCacheInterceptor
import com.burgstaller.okhttp.digest.CachingAuthenticator
import com.burgstaller.okhttp.digest.Credentials
import com.burgstaller.okhttp.digest.DigestAuthenticator
import com.ealva.brainzsvc.net.BrainzJsonFormatUserAgentInterceptor
import com.ealva.brainzsvc.net.CacheControlInterceptor
import com.ealva.brainzsvc.net.ThrottlingInterceptor
import com.ealva.ealvabrainz.log.BrainzLog
import com.ealva.ealvalog.lazyLogger
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.ConcurrentHashMap

private const val DAYS_MAX_AGE = 14
private const val DAYS_MIN_FRESH = 14
private const val DAYS_MAX_STALE = 365
private const val MUSICBRAINZ_MAX_CALLS_PER_SECOND = 1.0

private const val TEN_MEG = 10 * 1024 * 1024

private val LOG by lazyLogger("OkHttpClient.kt", BrainzLog.marker)

internal fun makeOkHttpClient(
  serviceName: String,
  appName: String,
  appVersion: String,
  contactEmail: String,
  cacheDirectory: File,
  credentialsProvider: CredentialsProvider? = null,
  addLoggingInterceptor: Boolean = true
): OkHttpClient = OkHttpClient.Builder().apply {
  if (credentialsProvider != null) {
    val authCache = ConcurrentHashMap<String, CachingAuthenticator>()
    authenticator(DigestAuthenticator(RuntimeCredentials(credentialsProvider)))
    addInterceptor(AuthenticationCacheInterceptor(authCache))
  }
  addInterceptor(CacheControlInterceptor(DAYS_MAX_AGE, DAYS_MIN_FRESH, DAYS_MAX_STALE))
  addInterceptor(ThrottlingInterceptor(MUSICBRAINZ_MAX_CALLS_PER_SECOND, serviceName))
  addInterceptor(BrainzJsonFormatUserAgentInterceptor(appName, appVersion, contactEmail))
  if (addLoggingInterceptor) {
    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
  }
  cache(Cache(cacheDirectory, TEN_MEG.toLong()))
}.build()

/**
 * Implement okhttp-digest Credentials so we don't have to create with username/pwd at construction
 * and instead get when needed. Client will decide how this info is obtained.
 */
private class RuntimeCredentials(private val provider: CredentialsProvider) : Credentials("", "") {
  override fun getUserName(): String {
    return provider.userName
  }

  override fun getPassword(): String {
    return provider.password
  }
}
