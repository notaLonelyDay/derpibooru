package me.lonelyday.derpibooru.ui.download

import android.content.Context
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock

interface DownloadProgressView {
    fun startDownload() {}
    fun setProgress(progress: Int) {}
    fun completeDownload() {}
    fun cancelDownload() {}
    fun showError() {}
}


class DownloadManager(
    context: Context,
    fetchConfiguration: FetchConfiguration
) {
    private val fetch = Fetch.Impl.getInstance(fetchConfiguration)

    // imageId to DPView
    private val viewsMap: BiMap<Int, DownloadProgressView> = HashBiMap.create()

    // downloadId to imageId
    private val internalIdMapper: BiMap<Int, Int> = HashBiMap.create()

    private val fetchListener = object : FetchListener {
        override fun onAdded(download: Download) {}

        override fun onCancelled(download: Download) {
            viewByDownloadId(download.id)?.cancelDownload()
        }

        override fun onCompleted(download: Download) {
            viewByDownloadId(download.id)?.completeDownload()
        }

        override fun onDeleted(download: Download) {
            viewByDownloadId(download.id)?.completeDownload()
        }

        override fun onDownloadBlockUpdated(
            download: Download,
            downloadBlock: DownloadBlock,
            totalBlocks: Int
        ) {
        }

        override fun onError(download: Download, error: Error, throwable: Throwable?) {}

        override fun onPaused(download: Download) {}

        override fun onProgress(
            download: Download,
            etaInMilliSeconds: Long,
            downloadedBytesPerSecond: Long
        ) {
            viewByDownloadId(download.id)?.setProgress(download.progress)
        }

        override fun onQueued(download: Download, waitingOnNetwork: Boolean) {}

        override fun onRemoved(download: Download) {}

        override fun onResumed(download: Download) {}

        override fun onStarted(
            download: Download,
            downloadBlocks: List<DownloadBlock>,
            totalBlocks: Int
        ) {
        }

        override fun onWaitingNetwork(download: Download) {}
    }

    init {
        fetch.addListener(fetchListener)
    }

    fun registerView(imageId: Int, view: DownloadProgressView) {
        viewsMap[imageId] = view
    }

    fun unregisterView(view: DownloadProgressView) {
        viewsMap.inverse().remove(view)
    }

    fun enqueue(imageId: Int, url: String, fileName: String) {
        val request = Request(url, fileName)
        request.networkType = NetworkType.ALL

        fetch.enqueue(request, {
            viewsMap[imageId]?.startDownload()
            internalIdMapper.inverse()[imageId] = it.id
        }, {
            viewsMap[imageId]?.showError()
        })
    }

    fun cancelDownload(imageId: Int) {
        val downloadId = internalIdMapper.inverse()[imageId]
        fetch.cancel(downloadId!!)
    }

    private fun viewByDownloadId(downloadId: Int): DownloadProgressView? {
        val imageId = internalIdMapper[downloadId]
        return viewsMap[imageId]
    }

}