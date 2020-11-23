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

@file:Suppress("Indentation", "MagicNumber", "unused")

package com.ealva.brainzapp.ui.artist

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import com.ealva.brainzapp.ui.view.sp
import com.ealva.brainzsvc.common.ArtistName
import com.ealva.brainzsvc.common.LabelName
import com.ealva.brainzsvc.common.ReleaseName
import com.ealva.brainzsvc.common.toReleaseName
import com.ealva.ealvabrainz.brainz.data.ArtistMbid
import com.ealva.ealvabrainz.brainz.data.LabelMbid
import com.ealva.ealvabrainz.brainz.data.ReleaseMbid
import com.ealva.ealvabrainz.brainz.data.toReleaseMbid
import me.gujun.android.span.span
import splitties.resources.appStyledColor

public class CreditItem(
  public val artistMbid: ArtistMbid,
  public val artistName: ArtistName,
  public val joinPhrase: String
)

@JvmName(name = "creditListToSpannable")
public fun List<CreditItem>.toSpannable(clicked: (CreditItem) -> Unit): SpannableStringBuilder {
  return span {
    this@toSpannable.forEachIndexed { index, credit ->
      if (index > 0) append(" ")
      span(credit.artistName.value) {
        textDecorationLine = "underline"
        textColor = Color.BLUE
        onClick = {
          clicked(credit)
        }
      }
      if (credit.joinPhrase.isNotBlank()) {
        append(" ")
        append(credit.joinPhrase)
      }
    }
  }
}

public class LabelItem(
  public val labelMbid: LabelMbid,
  public val name: LabelName,
  public val disambiguation: String
)

@JvmName(name = "labelListToSpannable")
public fun List<LabelItem>.toSpannable(
  ctx: Context,
  clicked: (LabelItem) -> Unit
): SpannableStringBuilder {
  return span {
    val disambiguationSize = ctx.sp(13)

    this@toSpannable.forEachIndexed { index, label ->
      if (index > 0) append(", ")
      span(label.name.value) {
        textDecorationLine = "underline"
        textColor = Color.BLUE
        onClick = { clicked(label) }
      }
      if (label.disambiguation.isNotBlank()) {
        span("(${label.disambiguation})") {
          textSize = disambiguationSize
          textColor = appStyledColor(android.R.attr.textColorSecondary)
          onClick = { clicked(label) }
        }
      }
    }
  }
}

public class ReleaseItem private constructor(
  public val id: Long,
  public val mbid: ReleaseMbid,
  public val name: ReleaseName,
  public val format: String,
  public val tracks: String,
  public val country: String,
  public val date: String,
  public val labels: List<LabelItem>,
  public val catalogNumber: String,
  public val barcode: String,
  public val artistCredits: List<CreditItem>
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ReleaseItem

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }

  override fun toString(): String {
    return "DisplayRelease(id=$id)"
  }

  public companion object {
    private var latestId = 0L
    public fun make(
      mbid: ReleaseMbid,
      name: ReleaseName,
      format: String,
      tracks: String,
      country: String,
      date: String,
      labels: List<LabelItem>,
      catalogNumber: String,
      barcode: String,
      artistCredits: List<CreditItem>
    ): ReleaseItem {
      ++latestId
      return ReleaseItem(
        latestId,
        mbid,
        name,
        format,
        tracks,
        country,
        date,
        labels,
        catalogNumber,
        barcode,
        artistCredits
      )
    }

    public val NullDisplayRelease: ReleaseItem =
      ReleaseItem(
        -1L,
        "".toReleaseMbid(),
        "".toReleaseName(),
        format = "",
        tracks = "",
        country = "",
        date = "",
        labels = emptyList(),
        catalogNumber = "",
        barcode = "",
        artistCredits = emptyList()
      )
  }
}
