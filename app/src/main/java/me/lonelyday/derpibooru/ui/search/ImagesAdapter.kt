package me.lonelyday.derpibooru.ui.search

import android.Manifest
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
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import me.lonelyday.derpibooru.DerpibooruApplication
import me.lonelyday.derpibooru.ui.download.DownloadProgressSearchItem
import me.lonelyday.derpibooru.ui.download.DownloadProgressView


class ImagesAdapter(
    private val downloadManager: me.lonelyday.derpibooru.ui.download.DownloadManager
) :
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

        return ImageViewHolder(view, downloadManager)
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }
}

class ImageViewHolder(
    private val view: View,
    private val downloadManager: me.lonelyday.derpibooru.ui.download.DownloadManager
) :
    RecyclerView.ViewHolder(view) {

    val context: Context = view.context
    private val binding = ItemSearchBinding.bind(view)
    private val imageView = binding.image
    private val artistsAdapter = (binding.artists.adapter as? ListAdapter<String, *>?)

    val downloadProgressView: DownloadProgressView = DownloadProgressSearchItem(view)

    val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val searchImageRepresentation: String =
        defaultSharedPreferences.getString("searchImageRepresentation", null)!!
    private val downloadImageRepresentation: String =
        defaultSharedPreferences.getString("downloadImageRepresentation", null)!!

    // Use only when recycling
    lateinit var image: Image

    var tagsRowHeight: Int? = null

    fun bindTo(image: Image) {
        this.image = image
        bindImage(image)
        artistsAdapter?.submitList(image.tag_names
            .filter { it.startsWith("artist:") }
            .map { it.removePrefix("artist:") }
        )
        bindDownload(image)
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

    fun recycle() {
        // Removing drawing progress
        downloadManager.unregisterView(this.image.id)
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
//                image.representations[searchImageRepresentation]
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

    private fun bindDownload(image: Image) {
        downloadManager.registerView(downloadProgressView, image.id)

        bindStartDownload(image)
    }

    private fun bindStartDownload(image: Image) {
        val url = image.representations[downloadImageRepresentation]
        val filename = image.name
        if (url == null) return

        binding.downloadButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                downloadManager.enqueue(url, filename, image.id)
                bindCancelDownload(image)
            } else {
                (context.applicationContext as? DerpibooruApplication)?.requestPermissionLauncher?.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun bindCancelDownload(image: Image) {
        binding.downloadButton.setOnClickListener {
            downloadManager.cancelDownload(image.id)
            bindStartDownload(image)
        }
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
                    collapseTags()
                    return false
                }
            })
    }

    private fun collapseTags() {
        binding.tags.layoutParams.height = tagsRowHeight!!
        binding.tags.requestLayout()
        binding.tagsExpander.setOnClickListener {
            expandTags()
        }
    }

    private fun expandTags() {
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
        binding.tagsExpander.setOnClickListener {
            collapseTags()
        }
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
