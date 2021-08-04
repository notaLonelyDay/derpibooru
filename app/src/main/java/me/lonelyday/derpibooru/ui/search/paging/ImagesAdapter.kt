package me.lonelyday.derpibooru.ui.search.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.db.vo.Image

class ImagesAdapter(private val glide: RequestManager) :
    PagingDataAdapter<Image, ImageViewHolder>(object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        val source = item?.view_url
        glide
            .load(item?.representations?.getOrDefault("large", source))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .priority(Priority.IMMEDIATE)
            .into(holder.image)
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return ImageViewHolder(view, glide)
    }
}

class ImageViewHolder(view: View, private val glide: RequestManager) :
    RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.image)
}