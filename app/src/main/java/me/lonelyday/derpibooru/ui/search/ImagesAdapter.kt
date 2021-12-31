package me.lonelyday.derpibooru.ui.search

import me.lonelyday.derpibooru.ui.download.DownloadManager
import me.lonelyday.derpibooru.ui.download.DownloadProgressView
import android.Manifest
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.*
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scaleMatrix
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.DEFAULT
import com.ablanco.zoomy.ZoomListener
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import me.lonelyday.derpibooru.DerpibooruApplication
import me.lonelyday.derpibooru.MainNavigationDirections
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.ItemSearchBinding
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.ui.ImageDiffUtilItemCallback
import me.lonelyday.derpibooru.ui.animateViewHeight
import me.lonelyday.derpibooru.ui.download.DownloadProgressSearchView
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifDrawableBuilder
import java.io.ByteArrayOutputStream
import com.ablanco.zoomy.Zoomy


class ImagesAdapter(
    private val downloadManager: DownloadManager,
    private val fragment: SearchFragment
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

        return ImageViewHolder(view, downloadManager, fragment)
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }
}

class ImageViewHolder(
    private val view: View,
    private val downloadManager: DownloadManager,
    private val fragment: SearchFragment
) :
    RecyclerView.ViewHolder(view) {

    val context: Context = view.context
    private val binding = ItemSearchBinding.bind(view)
    private val imageView = binding.image
    private val artistsAdapter = (binding.artists.adapter as? ListAdapter<String, *>?)

    private val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val searchImageRepresentation: String =
        defaultSharedPreferences.getString("searchImageRepresentation", "large")!!
    private val downloadImageRepresentation: String =
        defaultSharedPreferences.getString("downloadImageRepresentation", "large")!!

    private val downloadProgressView: DownloadProgressView =
        DownloadProgressSearchView(binding.downloadButton.root)

    private val requestPermissionLauncher =
        (context.applicationContext as? DerpibooruApplication)?.requestPermissionLauncher

    // Use only when recycling
    lateinit var image: Image

    var tagsExpandedHeight: Int? = null
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
        downloadManager.unregisterView(downloadProgressView)

        tagsRowHeight = null
        tagsExpandedHeight = null
    }

    private fun bindImage(image: Image) {
        val colorDrawable = ColorDrawable(Color.BLACK)
        val aspectRatio = image.width.toFloat() / image.height
        val bitmap = colorDrawable.toBitmap((100 * aspectRatio).toInt(), 100)
        val placeholderBg = bitmap.toDrawable(imageView.resources)

        val gifDrawable = GifDrawable(context.resources, R.drawable.luna_placeholder)

        val placeholder = LayerDrawable(
            arrayOf(
                placeholderBg,
//                progressDrawable
                gifDrawable
            )
        ).apply {
            setLayerInset(1, 300, 300, 300, 300)
        }

        Glide.with(imageView)
            .load(
                image.representations[searchImageRepresentation]
//                null as String?
            )
            .thumbnail(
                Glide.with(imageView)
                    .load(image.representations["thumb_tiny"])
            )
            .placeholder(placeholder)
            .priority(Priority.IMMEDIATE)
            .into(imageView)

        Zoomy.Builder(fragment.activity)
            .target(imageView)
            .enableImmersiveMode(false)
            .register()


//        imageView.setOnClickListener {
//            ViewCompat.setTransitionName(it, "item_image")
//            val extras = FragmentNavigatorExtras(it to "hero_image")
//            fragment.findNavController().navigate(
//                 TODO: check name
//                MainNavigationDirections.actionGlobalToFullScreenImageFragment(image),
//                extras
//            )

//        }
    }

    private fun bindDownload(image: Image) {
        downloadManager.registerView(image.id, downloadProgressView)

        bindStartDownload(image)
    }

    private fun bindStartDownload(image: Image) {
        val url = image.representations[downloadImageRepresentation]
        val filename =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath + image.name
        if (url == null) return

        binding.downloadButton.root.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                downloadManager.enqueue(image.id, url, filename)
                bindCancelDownload(image)
            } else {
                requestPermissionLauncher?.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun bindCancelDownload(image: Image) {
        binding.downloadButton.root.setOnClickListener {
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

        binding.tags.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                binding.tags.viewTreeObserver.removeOnPreDrawListener(this)
                tagsExpandedHeight =
                    binding.tags.height
                tagsRowHeight =
                    binding.tags.findViewById<View>(R.id.tagContainer)!!.height
                collapseTags(false)
                binding.tagsExpander.isVisible = tagsRowHeight!! < tagsExpandedHeight!!
                return false
            }
        })
    }

    private fun collapseTags(animated: Boolean = true) {
        binding.tagsExpander.setOnClickListener {
            expandTags()
        }

        // Animation
        if (animated) {
            binding.tagsContainer.animateViewHeight(tagsRowHeight!!, 300)
                .start()
        } else {
            binding.tagsContainer.layoutParams.height = tagsRowHeight!!
        }
        binding.tags.requestLayout()
        (AnimatorInflater.loadAnimator(
            context,
            R.animator.tags_expander_collapse_animation
        ) as AnimatorSet).apply {
            setTarget(binding.tagsExpander)
            start()
        }
    }

    @SuppressLint("ResourceType")
    private fun expandTags() {
        binding.tagsExpander.setOnClickListener {
            collapseTags()
        }

        // Animation
        binding.tagsContainer.animateViewHeight(tagsExpandedHeight!!, 300)
            .start()
        binding.tags.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.tags.requestLayout()
        (AnimatorInflater.loadAnimator(
            context,
            R.animator.tags_expander_expand_animation
        ) as AnimatorSet).apply {
            setTarget(binding.tagsExpander)
            start()
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