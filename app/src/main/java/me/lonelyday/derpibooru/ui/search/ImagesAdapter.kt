package me.lonelyday.derpibooru.ui.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.ui.ImageDiffUtilItemCallback

import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide


class ImagesAdapter(private val context: Context) :
    PagingDataAdapter<Image, ImageViewHolder>(ImageDiffUtilItemCallback()) {

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position) ?: return


        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        val image = holder.image

        val colorDrawable = ColorDrawable(Color.BLACK)
        val aspectRatio = item.width.toFloat() / item.height
        val bitmap = colorDrawable.toBitmap((100 * aspectRatio).toInt(), 100)

        Glide.with(context)
            .load(item.representations["large"])
            .thumbnail(
                Glide.with(context)
                    .load(item.representations["thumb_tiny"])
            )
            .placeholder(bitmap.toDrawable(context.resources))
            .priority(Priority.IMMEDIATE)
            .into(image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return ImageViewHolder(view)
    }
}

class ImageViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.image)
}