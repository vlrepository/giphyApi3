package app.android.giphyapinew.useCase

import app.android.giphyapinew.model.Response
import app.android.giphyapinew.utils.GiphyRepository

class GetGifsUseCase(
    private val apiRepository: GiphyRepository
) {
    suspend fun execute(category: String, limit: Int, offset: Int): Response {
        return apiRepository.getGifs(category, limit, offset)
    }
}