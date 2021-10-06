package me.lonelyday.derpibooru.ui.download

import android.view.View
import androidx.core.view.isVisible
import me.lonelyday.derpibooru.databinding.ButtonDownloadBinding

class DownloadProgressSearchView(private val view: View) : DownloadProgressView {

    private var _binding: ButtonDownloadBinding? = ButtonDownloadBinding.bind(view)
    private val binding get() = _binding!!
    private val context = view.context

    init {
        hideAll()
        binding.startDownloadDrawable.isVisible = true
    }

    override fun startDownload() {
        super.startDownload()
        hideAll()
        binding.progressDownloadBar.isVisible = true
    }

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        binding.progressDownloadBar.progress = progress
    }

    override fun completeDownload() {
        super.completeDownload()
        hideAll()
        binding.completeDownloadDrawable.isVisible = true
    }

    override fun cancelDownload() {
        super.cancelDownload()
        hideAll()
        binding.errorDownloadDrawable.isVisible = true
    }

    override fun showError() {
        super.showError()
        hideAll()
        binding.errorDownloadDrawable.isVisible = true
    }

    private fun hideAll() {
        binding.completeDownloadDrawable.isVisible = false
        binding.errorDownloadDrawable.isVisible = false
        binding.progressDownloadBar.isVisible = false
        binding.startDownloadDrawable.isVisible = false
    }
}