package app.android.giphyapinew.utils

import androidx.paging.PositionalDataSource
import app.android.giphyapinew.model.Datum
import app.android.giphyapinew.useCase.GetGifsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MyPositionalDataSource(
    private val useCase: GetGifsUseCase,
    private val category: String,
    private val scope: CoroutineScope,
    private val onLoadingCallback: (() -> Unit)
) : PositionalDataSource<Datum>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Datum>) {
        Timber.tag("MyPositionalDataSource")
            .w("loadInitial: requestedStartPosition = $params.requestedStartPosition")
        Timber.tag("MyPositionalDataSource")
            .w("loadInitial: requestedLoadSize = ${params.requestedLoadSize}")

        scope.launch {
            val result = useCase.execute(
                category = category,
                offset = params.requestedStartPosition,
                limit = params.requestedLoadSize
            )
            onLoadingCallback.invoke()
            callback.onResult(result.data, 0)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Datum>) {
        Timber.tag("MyPositionalDataSource").w("loadRange: startPosition = ${params.startPosition}")
        Timber.tag("MyPositionalDataSource").w("loadRange: loadSize = ${params.loadSize}")

        scope.launch {
            val result = useCase.execute(
                category = category,
                offset = params.startPosition,
                limit = params.loadSize
            )
            callback.onResult(result.data)
        }
    }
}