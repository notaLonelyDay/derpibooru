package me.lonelyday.derpibooru.ui.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.DEFAULT
import com.bumptech.glide.Priority
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.ui.ImageDiffUtilItemCallback

import com.bumptech.glide.Glide
import me.lonelyday.derpibooru.databinding.ItemSearchBinding


class ImagesAdapter(private val context: Context) :
    PagingDataAdapter<Image, ImageViewHolder>(ImageDiffUtilItemCallback()) {

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bindTo(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return ImageViewHolder(view)
    }
}

class ImageViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    val context: Context = view.context
    private val binding = ItemSearchBinding.bind(view)
    private val imageView = binding.image


    fun bindTo(image: Image) {
        val colorDrawable = ColorDrawable(Color.BLACK)
        val aspectRatio = image.width.toFloat() / image.height
        val bitmap = colorDrawable.toBitmap((100 * aspectRatio).toInt(), 100)
        val placeholderBg = bitmap.toDrawable(this.imageView.resources)

        val progressDrawable = CircularProgressDrawable(context).apply {
            setStyle(DEFAULT)
            setColorSchemeColors(Color.CYAN)
            centerRadius = 0.9f
            strokeWidth = 0.3f
            start()
        }
        val placeholder = LayerDrawable(
            arrayOf(
                placeholderBg,
                progressDrawable
            )
        )

        Glide.with(imageView)
            .load(image.representations["large"])
//            .thumbnail(
//                Glide.with(imageView)
//                    .load(image.representations["thumb_tiny"])
//            )
            .placeholder(placeholder)
            .priority(Priority.IMMEDIATE)
            .into(imageView)
    }
}