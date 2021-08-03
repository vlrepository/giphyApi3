package app.android.giphyapinew.utils

import app.android.giphyapinew.interfaces.GiphyApiInterface
import app.android.giphyapinew.model.Response

abstract class GiphyRepository {
    abstract suspend fun getGifs(category: String, limit: Int, offset: Int): Response
}

class GiphyRepositoryImpl(private val api: GiphyApiInterface) : GiphyRepository() {
    override suspend fun getGifs(category: String, limit: Int, offset: Int): Response {
        return api.search(
            category = category,
            limit = limit,
            offset = offset
        )
    }
}