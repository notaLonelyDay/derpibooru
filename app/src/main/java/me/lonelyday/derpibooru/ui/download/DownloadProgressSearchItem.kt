package me.lonelyday.derpibooru.ui.download

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import me.lonelyday.derpibooru.databinding.ItemSearchBinding

class DownloadProgressSearchItem(private val view: View?) : DownloadProgressView {

    private var _binding: ItemSearchBinding? = ItemSearchBinding.bind(view!!)
    private val binding get() = _binding!!
    private val context = view!!.context
    private val defaultDrawable: Drawable = binding.downloadButton.drawable

    private val progressDrawable: Drawable by lazy {
        val progressDrawable = CircularProgressDrawable(context)
        progressDrawable.backgroundColor = Color.BLACK
        progressDrawable
    }

    override fun isValid(): Boolean = view != null

    override fun startProgress() {
        if (!isValid()) return
        binding.downloadButton.setImageDrawable(progressDrawable)
    }

    override fun stopProgress() {
        if (!isValid()) return

        binding.downloadButton.setImageDrawable(defaultDrawable)
    }
}