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

package com.ealva.ealvabrainz.brainz.data

import com.ealva.ealvabrainz.brainz.data.ReleaseEvent.Companion.NullReleaseEvent
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class ReleaseEvent(
  public var date: String = "",
  @field:FallbackOnNull public var area: Area = Area.NullArea
) {
  public companion object {
    public val NullReleaseEvent: ReleaseEvent = ReleaseEvent()
    public val fallbackMapping: Pair<String, Any> =
      ReleaseEvent::class.java.name to NullReleaseEvent
  }
}

public inline val ReleaseEvent.isNullObject: Boolean
  get() = this === NullReleaseEvent
