package app.android.giphyapinew.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    toast(requireContext(), text, duration)

private fun toast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(context, text, duration).show()