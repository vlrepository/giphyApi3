package app.android.giphyapinew.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.android.giphyapinew.R
import app.android.giphyapinew.databinding.ItemGifBinding
import app.android.giphyapinew.model.Datum
import com.bumptech.glide.Glide
import timber.log.Timber

//class GiphyAdapter : ListAdapter<Datum, GiphyAdapter.ViewHolder>(GifDiffUtilCallback()) {
class GiphyAdapter : PagedListAdapter<Datum, GiphyAdapter.ViewHolder>(GifDiffUtilCallback()) {
    var onClick: ((Datum) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gif, parent, false)*/
        val itemBinding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
        else Timber.tag("GiphyAdapter").e("onBindViewHolder: item($position) = null")
    }

    inner class ViewHolder(itemBinding: ItemGifBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val tvName = itemBinding.tvItem
        private val ivGif = itemBinding.ivItem

        fun bind(datum: Datum) {
            tvName.text = datum.title
            val fixedHeightDownSampled = datum.images?.fixedHeightDownSampled
            Glide.with(itemView.context)
                .load(fixedHeightDownSampled?.url)
                .placeholder(R.drawable.ic_default_placeholder)
                .into(ivGif)

            itemView.setOnClickListener {
                onClick?.invoke(datum)
            }
        }
    }
}

class GifDiffUtilCallback : DiffUtil.ItemCallback<Datum>() {
    override fun areItemsTheSame(oldItem: Datum, newItem: Datum): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Datum, newItem: Datum): Boolean {
        return false
    }
}