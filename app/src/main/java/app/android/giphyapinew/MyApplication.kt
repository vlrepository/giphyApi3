package app.android.giphyapinew

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        startKoin {
            Log.w("kjsckdc", "START KOIN")
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority == Log.ERROR) {
                        if (t != null && t !is ConnectException && t !is UnknownHostException) {
                            try {
//                            FirebaseCrashlytics.getInstance().recordException(t)
                            } catch (ignore: Exception) {
                                //ignore
                            }
                        }
                    }
                }
            })
        }
    }
}