package app.android.giphyapinew.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.giphyapinew.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    private val _defaultErrorLiveData = SingleLiveEvent<Throwable>()
    private val _defaultMessageResLiveData = SingleLiveEvent<Int>()
    private val _defaultMessageLiveData = SingleLiveEvent<String>()

    val defaultErrorLiveData: LiveData<Throwable> = _defaultErrorLiveData
    val defaultMessageResLiveData: LiveData<Int> = _defaultMessageResLiveData
    val defaultMessageLiveData: LiveData<String> = _defaultMessageLiveData

//    lateinit var router: Router

    private val defaultErrorHandler = CoroutineExceptionHandler { _, throwable ->
        _defaultErrorLiveData.value = throwable
        Timber.e(throwable, "ViewModel exception handler")
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(defaultErrorHandler) {
            this.block()
        }
    }
}