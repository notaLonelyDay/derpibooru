package me.lonelyday.derpibooru.ui.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.DEFAULT
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.ItemSearchBinding
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.ui.ImageDiffUtilItemCallback


class ImagesAdapter(private val context: Context) :
    PagingDataAdapter<Image, ImageViewHolder>(ImageDiffUtilItemCallback()) {

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bindTo(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)

        // setup artists lisT
        val artistsAdapter = ArtistsAdapter()
        view.findViewById<RecyclerView>(R.id.artists)?.let {
            it.adapter = artistsAdapter
        }

        return ImageViewHolder(view)
    }
}

class ImageViewHolder(
    view: View
) :
    RecyclerView.ViewHolder(view) {

    val context: Context = view.context
    private val binding = ItemSearchBinding.bind(view)
    private val imageView = binding.image
    private val artistsAdapter = (binding.artists.adapter as? ListAdapter<String, *>?)

    var tagsRowHeight: Int? = null


    fun bindTo(image: Image) {
        bindImage(image)
        artistsAdapter?.submitList(image.tag_names
            .filter { it.startsWith("artist:") }
            .map { it.removePrefix("artist:") }
        )
        binding.faves.text = image.faves.toString()
        binding.upvotes.text = image.upvotes.toString()
        binding.score.text = image.score.toString()
        binding.downvotes.text = image.downvotes.toString()
        binding.uploader.text = image.uploader
        binding.uploadedAt.text = image.updated_at.toString()
        binding.width.text = image.width.toString()
        binding.height.text = image.height.toString()
        binding.format.text = image.format
        binding.size.text = image.size.toString()
        binding.description.text = image.description
        bindTags(image.tag_names zip image.tag_ids)
    }

    private fun bindTags(tags: List<Pair<String, Int>>) {
        // inflating tags
        binding.tags.removeAllViews()
        for ((text, id) in tags) {
            val tagView = LayoutInflater.from(context)
                .inflate(R.layout.item_search_tag_item, binding.tags, false)
            tagView.findViewById<TextView>(R.id.tag).text = text
            binding.tags.addView(tagView)
        }

        if (tagsRowHeight != null) {
            binding.tags.layoutParams.height = tagsRowHeight!!
        } else
            binding.tags.viewTreeObserver.addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    binding.tags.viewTreeObserver.removeOnPreDrawListener(this)
                    tagsRowHeight =
                        binding.tags.findViewById<View>(R.id.tagContainer)!!.measuredHeight
                    binding.tags.layoutParams.height = tagsRowHeight!!
                    return false
                }
            })
        binding.tagsExpander.setOnClickListener {
            binding.tags.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.tags.requestLayout()
//            it.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    it.viewTreeObserver.removeOnPreDrawListener(this)
//                    ObjectAnimator.ofInt(binding.tags.layoutParams, "height", 50, 100)
//                        .apply {
//                            duration = 1000
//                            start()
//                        }
//                    Log.d("tea", "bindTo: ${binding.tags.measuredHeight}")
//                    return false
//                }
//            })
        }
    }

    private fun bindImage(image: Image) {
        val colorDrawable = ColorDrawable(Color.BLACK)
        val aspectRatio = image.width.toFloat() / image.height
        val bitmap = colorDrawable.toBitmap((100 * aspectRatio).toInt(), 100)
        val placeholderBg = bitmap.toDrawable(imageView.resources)

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
            .load(
//                image.representations["tall"]
                null as String?
            )
            .thumbnail(
//                Glide.with(imageView)
//                    .load(image.representations["thumb_tiny"])
            )
            .placeholder(placeholder)
            .priority(Priority.IMMEDIATE)
            .into(imageView)
    }
}

class ArtistsAdapter :
    ListAdapter<String, ArtistViewHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }) {

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.view.findViewById<TextView>(R.id.artist)?.let {
            it.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_artist_item, parent, false)
        return ArtistViewHolder(view)
    }
}

class ArtistViewHolder(val view: View) : RecyclerView.ViewHolder(view)
