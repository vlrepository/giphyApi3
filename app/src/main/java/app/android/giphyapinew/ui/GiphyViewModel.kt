package app.android.giphyapinew.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import app.android.giphyapinew.base.BaseViewModel
import app.android.giphyapinew.model.Datum
import app.android.giphyapinew.useCase.GetGifsUseCase
import app.android.giphyapinew.utils.MyPositionalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class GiphyViewModel : BaseViewModel() {
    abstract val isLoadingLiveData: MutableLiveData<Boolean>
    abstract val pagedListLiveData: MutableLiveData<PagedList<Datum>>
    abstract fun getGifs(category: String)
    abstract fun setIsLoading(isLoading: Boolean)
    abstract fun getDataSource(category: String)
}

class GiphyViewModelImpl(
    private val getGifsUseCase: GetGifsUseCase/*,
    private val router: Router*/
) : GiphyViewModel() {
    override val isLoadingLiveData = MutableLiveData<Boolean>()
    override val pagedListLiveData = MutableLiveData<PagedList<Datum>>()
    private var page = 0
    private var lastGifCategory: String? = null

    override fun getGifs(category: String) {
        /*launch {
            try {
                val response = getGifsUseCase.execute(category, 1)
                dataMutableLiveData.postValue(response.data)
                Timber.tag("GiphyViewModel").i("Receiving GIFs successfully")
            } catch (exc: Exception) {
                Timber.tag("GiphyViewModel").e(exc)
            }
        }*/
    }

    override fun setIsLoading(isLoading: Boolean) {
        isLoadingLiveData.value = isLoading
    }

    override fun getDataSource(category: String) {
        isLoadingLiveData.value = true

        // Hide loading progress bar callback
        val onLoadingFinish = {
            isLoadingLiveData.value = false
        }

        // DataSource
        val dataSource = MyPositionalDataSource(
            useCase = getGifsUseCase,
            category = category,
            scope = viewModelScope,
            onLoadingCallback = onLoadingFinish
        )

        // PagedList
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        launch {
            val pagedList = PagedList.Builder(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(MainThreadExecutor())
                .build()

            withContext(Dispatchers.Main) {
                pagedListLiveData.value = pagedList
            }
        }
    }
}

internal class MainThreadExecutor : Executor {
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mHandler.post(command)
    }
}