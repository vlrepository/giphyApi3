package app.android.giphyapinew

import app.android.giphyapinew.interfaces.GiphyApiInterface
import app.android.giphyapinew.ui.GiphyViewModel
import app.android.giphyapinew.ui.GiphyViewModelImpl
import app.android.giphyapinew.useCase.GetGifsUseCase
import app.android.giphyapinew.utils.*
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel<GiphyViewModel> { GiphyViewModelImpl(get()) }
}

val useCasesModule = module {
    factory { GetGifsUseCase(get()) }
}

val retrofitModule = module {
    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<ApiKeyInterceptor>())
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BODY)
                    .log(Platform.INFO)
                    .tag(Constants.TAG)
                    .build()
            ).build()
    }
    factory {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    factory { ApiKeyInterceptor() }
    factory { get<Retrofit>().create(GiphyApiInterface::class.java) }
    single<GiphyRepository> { GiphyRepositoryImpl(get()) }
}

val appModules = listOf(viewModelModule, useCasesModule, retrofitModule)