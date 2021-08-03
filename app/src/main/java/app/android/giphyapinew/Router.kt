package app.android.giphyapinew

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import timber.log.Timber

class Router(
    private val activity: Activity,
    @IdRes private val viewId: Int
) {

    fun navigateUp() {
        val navController = activity.findNavController(viewId)
        navController.navigateUp()
    }

    fun navigate(@IdRes resId: Int) {
        try {
            val navController = activity.findNavController(viewId)
            navController.navigate(resId)
        } catch (exc: Exception) {
            Timber.e(exc)
        }
    }

    fun navigate(@IdRes resId: Int, bundle: Bundle) {
        try {
            val navController = activity.findNavController(viewId)
            navController.navigate(resId, bundle)
        } catch (exc: Exception) {
            Timber.e(exc)
        }
    }

    fun navigate(navDirections: NavDirections) {
        try {
            val navController = activity.findNavController(viewId)
            navController.navigate(navDirections)
        } catch (exc: Exception) {
            Timber.e(exc)
        }
    }

    fun addOnDestinationChangedListener(listener: NavController.OnDestinationChangedListener) {
        val navController = activity.findNavController(viewId)
        navController.addOnDestinationChangedListener(listener)
    }

    fun removeOnDestinationChangedListener(listener: NavController.OnDestinationChangedListener) {
        val navController = activity.findNavController(viewId)
        navController.removeOnDestinationChangedListener(listener)
    }
}