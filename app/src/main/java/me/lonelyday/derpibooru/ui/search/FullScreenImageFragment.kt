package me.lonelyday.derpibooru.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.databinding.FragmentFullScreenImageBinding
import java.util.concurrent.TimeUnit

class FullScreenImageFragment: Fragment() {
//    private var _binding: FragmentFullScreenImageBinding? = null
//    private val binding get() = _binding!!
//
//    val args: FullScreenImageFragmentArgs by navArgs()
//    val image by lazy { args.image }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//        sharedElementEnterTransition = TransitionInflater.from(requireContext())
//            .inflateTransition(R.transition.test_transition)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentFullScreenImageBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        prepareSharedElementTransition()
//        loadImage()
//    }
//    private fun loadImage(){
////        val colorDrawable = ColorDrawable(Color.BLACK)
////        val aspectRatio = image.width.toFloat() / image.height
////        val bitmap = colorDrawable.toBitmap((100 * aspectRatio).toInt(), 100)
////        val placeholderBg = bitmap.toDrawable(binding.image.resources)
////
////        val gifDrawable = GifDrawable(requireContext().resources, R.drawable.luna_placeholder)
////
////        val placeholder = LayerDrawable(
////            arrayOf(
////                placeholderBg,
//////                progressDrawable
////                gifDrawable
////            )
////        ).apply {
////            setLayerInset(1, 300, 300, 300, 300)
////        }
//
//        Glide.with(binding.image)
//            .load(
//                image.representations["large"]
//            )
//            .priority(Priority.IMMEDIATE)
//            .into(binding.image)
//    }
//
//    private fun prepareSharedElementTransition(){
//        ViewCompat.setTransitionName(binding.image, "hero_image")
//        postponeEnterTransition(100, TimeUnit.MILLISECONDS)
//
//        setExitSharedElementCallback(object : SharedElementCallback() {
//            override fun onMapSharedElements(
//                names: MutableList<String>?,
//                sharedElements: MutableMap<String, View>?
//            ) {
//                super.onMapSharedElements(names, sharedElements)
//
//                // Map the first shared element name to the child ImageView.
//                sharedElements!![names!![0]] = view!!.findViewById(R.id.image)
//            }
//        })
//    }
}