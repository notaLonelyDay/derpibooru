package me.lonelyday.derpibooru.ui.download

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

interface DownloadProgressView {
    fun isValid(): Boolean
    fun startProgress()
    fun stopProgress()
}

class DownloadManager(
    private val downloadManager: DownloadManager,
    private val context: Context
) {
    // downloadId to Image.id
    // this map will be saved to preference
    private val downloadsMap: BiMap<Long, Int> = HashBiMap.create()

    // Image.id to View
    private val viewsMap: MutableMap<Int, DownloadProgressView> = mutableMapOf()

    private lateinit var broadcastReceiver: DownloadsBroadcastReceiver

    init {
        startBroadcastReceiver()
    }


    @SuppressLint("Range")
    fun registerView(downloadProgressView: DownloadProgressView, imageId: Int) {
        viewsMap[imageId] = downloadProgressView
        val downloadId = downloadsMap.inverse()[imageId]
        downloadId?.let {
            val cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            assert(cursor.count != 0)
            cursor.moveToFirst()
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            updateStatus(downloadId, status)
//            cursor.getString(DownloadManager.COLUMN_STATUS)
        }
    }

    fun unregisterView(imageId: Int) {
        viewsMap.remove(imageId)
    }

    fun enqueue(url: String, filename: String, imageId: Int) {
        val request = defaultRequestBuilder(url)
            .setTitle(filename)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        val downloadId = downloadManager.enqueue(request)
        downloadsMap[downloadId] = imageId

        updateStatus(downloadId, DownloadManager.STATUS_PENDING)
    }

    fun updateStatus(downloadId: Long, status: Int) {
        val imageId = downloadsMap[downloadId]
        assert(imageId != null)
        when (status) {
            DownloadManager.STATUS_SUCCESSFUL -> {
                viewsMap[imageId]?.stopProgress()
                downloadsMap.remove(downloadId)
            }
            DownloadManager.STATUS_FAILED -> {
                viewsMap[imageId]?.stopProgress()
                downloadsMap.remove(downloadId)
            }
            DownloadManager.STATUS_RUNNING,
            DownloadManager.STATUS_PENDING,
            DownloadManager.STATUS_PAUSED -> {
                viewsMap[imageId]?.startProgress()
            }
        }
    }

    fun cancelDownload(imageId: Int) {
        val downloadId = downloadsMap.inverse()[imageId]
        assert(downloadId == null) {
            "You're trying to cancel download that not in map"
        }
        downloadId!!
        downloadManager.remove(downloadId)
        updateStatus(downloadId, DownloadManager.STATUS_FAILED)
    }

    private fun defaultRequestBuilder(url: String): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(url))
            .setDescription("File is downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    private fun startBroadcastReceiver() {
        broadcastReceiver = DownloadsBroadcastReceiver(this)
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        context.registerReceiver(broadcastReceiver, filter)
    }
}

class DownloadsBroadcastReceiver(
    private val downloadManager: me.lonelyday.derpibooru.ui.download.DownloadManager
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null)
            return
        if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
            if (downloadId != 0L) {
                downloadManager.updateStatus(downloadId, DownloadManager.STATUS_SUCCESSFUL)
            }
        }
    }
}