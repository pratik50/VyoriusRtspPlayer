package com.pratik.vyoriusrtspplayer.utils

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import java.io.File

object FileUtils {

    var lastRecordedPath: String? = null

    fun getRecordingFilePath(context: Context): String? {
        val outputDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "VyoriusRTSP")
        if (!outputDir.exists()) {
            val success = outputDir.mkdirs()
            if (!success) return null
        }
        val fileName = "stream_${System.currentTimeMillis()}.mp4"
        val path = File(outputDir, fileName).absolutePath
        lastRecordedPath = path
        return path
    }

    fun scanFileToGallery(context: Context, filePath: String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(filePath),
            arrayOf("video/mp4"),
            null
        )
    }
}