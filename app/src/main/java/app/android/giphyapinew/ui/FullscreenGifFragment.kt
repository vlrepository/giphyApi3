package app.android.giphyapinew.ui

import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import app.android.giphyapinew.base.BaseFragment
import app.android.giphyapinew.databinding.FragmentFullscreenGifBinding
import app.android.giphyapinew.model.Datum
import com.bumptech.glide.Glide
import kotlin.math.max
import kotlin.math.min


class FullscreenGifFragment : BaseFragment<FullscreenGifViewModel, FragmentFullscreenGifBinding>() {
    private lateinit var mScaleGestureDetector: ScaleGestureDetector

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            val gif = bundle.getParcelable<Datum>(GiphyFragment.KEY)
            if (gif != null) {
//                binding.tvItem.text = gif.title
                val fixedHeightDownSampled = gif.images?.fixedHeightDownSampled
                Glide.with(requireContext()).load(fixedHeightDownSampled?.url).into(binding.ivItem)
            }
        }

        mScaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener(binding))

        binding.ivItem.setOnTouchListener { v, event ->
            mScaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    private class ScaleListener(
        private val binding: FragmentFullscreenGifBinding
    ) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor
            mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
            binding.ivItem.scaleX = mScaleFactor
            binding.ivItem.scaleY = mScaleFactor
            return true
        }
    }

    companion object {
        var mScaleFactor = 1.0f
    }
}