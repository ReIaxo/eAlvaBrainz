/*
 * Copyright (c) 2021  Eric A. Snell
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

package com.ealva.brainzsvc.common

import com.ealva.ealvabrainz.common.AreaMbid
import com.ealva.ealvabrainz.common.ArtistMbid
import com.ealva.ealvabrainz.common.CollectionMbid
import com.ealva.ealvabrainz.common.EditorName
import com.ealva.ealvabrainz.common.EventMbid
import com.ealva.ealvabrainz.common.LabelMbid
import com.ealva.ealvabrainz.common.PlaceMbid
import com.ealva.ealvabrainz.common.RecordingMbid
import com.ealva.ealvabrainz.common.ReleaseGroupMbid
import com.ealva.ealvabrainz.common.ReleaseMbid
import com.ealva.ealvabrainz.common.TrackMbid
import com.ealva.ealvabrainz.common.WorkMbid

public interface QueryMapItem {
  public fun put(map: QueryMap): QueryMap
}

public open class AreaQueryMapItem(private val mbid: AreaMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.area(mbid)
}

public open class ArtistQueryMapItem(private val mbid: ArtistMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.artist(mbid)
}

public open class CollectionQueryMapItem(private val mbid: CollectionMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.collection(mbid)
}

public open class EditorQueryMapItem(private val editorName: EditorName) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.editor(editorName)
}

public open class EventQueryMapItem(private val mbid: EventMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.event(mbid)
}

public open class LabelQueryMapItem(private val mbid: LabelMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.label(mbid)
}

public open class PlaceQueryMapItem(private val mbid: PlaceMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.place(mbid)
}

public open class RecordingQueryMapItem(private val mbid: RecordingMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.recording(mbid)
}

public open class ReleaseQueryMapItem(private val mbid: ReleaseMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.release(mbid)
}

public open class ReleaseGroupQueryMapItem(private val mbid: ReleaseGroupMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.releaseGroup(mbid)
}

public open class TrackArtistQueryMapItem(private val mbid: ArtistMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.trackArtist(mbid)
}

public open class TrackQueryMapItem(private val mbid: TrackMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.track(mbid)
}

public open class WorkQueryMapItem(private val mbid: WorkMbid) : QueryMapItem {
  override fun put(map: QueryMap): QueryMap = map.work(mbid)
}
