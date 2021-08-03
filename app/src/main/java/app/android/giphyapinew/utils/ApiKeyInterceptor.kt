package app.android.giphyapinew.utils

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter("api_key", "dvVxO5Yhd3nlfzSzNDbzvo1GZIQsSRLu")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}