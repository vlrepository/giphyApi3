package app.android.giphyapinew.ext

import android.view.View

fun View.isVisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}