package me.lonelyday.derpibooru.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import me.lonelyday.derpibooru.MainNavigationDirections
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentFullScreenImageBinding
import me.lonelyday.derpibooru.databinding.FragmentSearchBinding
import me.lonelyday.derpibooru.ui.download.DownloadManager
import pl.droidsonroids.gif.GifDrawable
import javax.inject.Inject

class FullScreenImageFragment: Fragment() {
    private var _binding: FragmentFullScreenImageBinding? = null
    private val binding get() = _binding!!

    val args: FullScreenImageFragmentArgs by navArgs()
    val image by lazy { args.image }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullScreenImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImage()


    }
    private fun loadImage(){
        val colorDrawable = ColorDrawable(Color.BLACK)
        val aspectRatio = image.width.toFloat() / image.height
        val bitmap = colorDrawable.toBitmap((100 * aspectRatio).toInt(), 100)
        val placeholderBg = bitmap.toDrawable(binding.image.resources)

        val gifDrawable = GifDrawable(requireContext().resources, R.drawable.luna_placeholder)

        val placeholder = LayerDrawable(
            arrayOf(
                placeholderBg,
//                progressDrawable
                gifDrawable
            )
        ).apply {
            setLayerInset(1, 300, 300, 300, 300)
        }

        Glide.with(binding.image)
            .load(
                image.representations["large"]
//                null as String?
            )
            .thumbnail(
                Glide.with(binding.image)
                    .load(image.representations["thumb_tiny"])
            )
            .placeholder(placeholder)
            .priority(Priority.IMMEDIATE)
            .into(binding.image)
    }
}