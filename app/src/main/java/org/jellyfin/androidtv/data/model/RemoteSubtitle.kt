package org.jellyfin.androidtv.data.model

data class RemoteSubtitle constructor(
	val id: String = "",
	val name: String = "",
	val language: String = "",
	val providerName: String = "",
	val downloadCount: Int = 0,
	val isHashMatch: Boolean = false
)
