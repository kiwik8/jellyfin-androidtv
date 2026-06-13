package org.jellyfin.androidtv.data.mapper

import org.jellyfin.androidtv.data.model.RemoteSubtitle
import org.jellyfin.sdk.model.api.RemoteSubtitleInfo

fun RemoteSubtitleInfo.toRemoteSubtitle(): RemoteSubtitle {
	return RemoteSubtitle(
		id = this.id ?: "",
		name = this.name ?: "Unnamed",
		language = this.threeLetterIsoLanguageName ?: "und",
		providerName = this.providerName ?: "Unknown",
		downloadCount = this.downloadCount ?: 0,
		isHashMatch = this.isHashMatch ?: false
	)
}
