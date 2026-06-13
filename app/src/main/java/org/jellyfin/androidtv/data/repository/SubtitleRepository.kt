package org.jellyfin.androidtv.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jellyfin.androidtv.data.mapper.toRemoteSubtitle
import org.jellyfin.androidtv.data.model.RemoteSubtitle
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.subtitleApi
import org.jellyfin.sdk.model.UUID
import timber.log.Timber

interface SubtitleRepository {
	suspend fun searchRemoteSubtitles(itemId: UUID, language: String): Result<List<RemoteSubtitle>>
	suspend fun downloadAndSave(itemId: UUID, subtitleId: String): Result<Unit>
}

class SubtitleRepositoryImpl(
	private val api: ApiClient
): SubtitleRepository {
	override suspend fun searchRemoteSubtitles(itemId: UUID, language: String): Result<List<RemoteSubtitle>>
		= withContext(Dispatchers.IO) {
			runCatching {
				val response = api.subtitleApi.searchRemoteSubtitles(
					itemId = itemId,
					language = language,
					isPerfectMatch = false,
				)
				response.content.map { it.toRemoteSubtitle() }
			}.onFailure { e ->
				Timber.e(e, "SubtitleRepository: Exception in searchRemoteSubtitles for $itemId")
			}
	}

	override suspend fun downloadAndSave(itemId: UUID, subtitleId: String) = withContext(Dispatchers.IO){
		runCatching {
			api.subtitleApi.downloadRemoteSubtitles(
				itemId = itemId,
				subtitleId = subtitleId
			)
			Unit
		}.onFailure {
			e -> Timber.e(e, "SubtitleRepository: Exception in downloadAndSave for $itemId")
		}
	}
}
