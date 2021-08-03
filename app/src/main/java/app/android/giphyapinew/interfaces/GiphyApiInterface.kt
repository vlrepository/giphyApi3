package app.android.giphyapinew.interfaces

import app.android.giphyapinew.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiInterface {
    @GET("search")
    suspend fun search(
        @Query("q") category: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int
    ): Response
}