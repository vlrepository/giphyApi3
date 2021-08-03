package app.android.giphyapinew.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.android.giphyapinew.R
import app.android.giphyapinew.Router
import app.android.giphyapinew.base.BaseFragment
import app.android.giphyapinew.databinding.FragmentGiphyBinding
import app.android.giphyapinew.ext.isVisible
import app.android.giphyapinew.ext.showToast
import app.android.giphyapinew.ext.subscribe
import org.koin.android.ext.android.inject
import java.util.*

class GiphyFragment : BaseFragment<GiphyViewModel, FragmentGiphyBinding>() {
    private lateinit var giphyAdapter: GiphyAdapter
    private var timer: Timer? = null
    private val delay: Long = 200
    private val router by inject<Router>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        giphyAdapter = GiphyAdapter()
        binding.rvGifs.adapter = giphyAdapter
        binding.rvGifs.layoutManager = LinearLayoutManager(context)

        /*binding.etType.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 2) {
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            val category = binding.etType.text
                            if (!category.isNullOrBlank()) {
                                viewModel.getGifs(category.toString())
                            }
                        }
                    }, delay)
                } else makeToast("too short request")
            }
        })*/

        binding.btnSearch.setOnClickListener {
            val category = binding.etType.text
            if (!category.isNullOrBlank()) {
                viewModel.getDataSource(category = category.toString())
            } else {
                showToast("Input category", Toast.LENGTH_SHORT)
            }
        }

        subscribe(viewModel.pagedListLiveData) {
            giphyAdapter.submitList(it)
        }

        subscribe(viewModel.isLoadingLiveData) {
            binding.pbLoading.isVisible(it)
        }

        giphyAdapter.onClick = {
            val bundle = Bundle()
            bundle.putParcelable(KEY, it)

            findNavController().navigate(R.id.action_giphyFragment_to_fullscreenGifFragment, bundle)
        }
    }

    companion object {
        const val KEY = "ksjdnc"
    }
}