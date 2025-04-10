package com.pratik.vyoriusrtspplayer.vlc

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.pratik.vyoriusrtspplayer.utils.FileUtils
import com.pratik.vyoriusrtspplayer.utils.VlcUtils
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.MediaPlayer
import java.io.File

class VlcRecorder(private val context: Context) {

    private val libVLC: LibVLC
    private var recorderPlayer: MediaPlayer? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        val options = arrayListOf(
            "--rtsp-tcp",
            "--avcodec-hw=auto",
            "--network-caching=1000",
            "--file-caching=1000",
            "--live-caching=1000",
            "--clock-jitter=500",
            "--no-drop-late-frames"
        )
        libVLC = VlcUtils.createLibVLC(context, options)
    }

    fun startRecording(url: String) {

        val outputPath = FileUtils.getRecordingFilePath(context) ?: run {
            showToast("Failed to create recording folder", Toast.LENGTH_LONG)
            return
        }

        recorderPlayer = MediaPlayer(libVLC).apply {
            val media = VlcUtils.createMedia(libVLC as LibVLC, url).apply {
                addOption(":network-caching=1000")
                addOption(":file-caching=1000")
                addOption(":live-caching=1000")
                val escapedPath = outputPath.replace(" ", "%20")
                val sout = "#std{access=file,mux=mp4,dst=$escapedPath}"
                addOption(":sout=$sout")
                addOption(":sout-keep")
            }
            this.media = media
            media.release()
            setEventListener(VlcUtils.getRecordingEventListener(context))
            play()
        }

        showToast("Recording started")
    }

    fun stopRecording() {
        recorderPlayer?.let { player ->
            player.stop()
            mainHandler.postDelayed({
                player.release()
                val path = FileUtils.lastRecordedPath
                recorderPlayer = null

                path?.let {
                    val file = File(it)
                    if (file.exists() && file.length() > 0) {
                        FileUtils.scanFileToGallery(context, it)
                        showToast("Recording saved to: $it", Toast.LENGTH_LONG)
                    } else {
                        showToast("Recording failed to save!", Toast.LENGTH_LONG)
                    }
                }
                FileUtils.lastRecordedPath = null
            }, 500)
        } ?: showToast("No recording in progress!")
    }

    fun release() {
        recorderPlayer?.stop()
        recorderPlayer?.release()
        recorderPlayer = null
        libVLC.release()
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        mainHandler.post { Toast.makeText(context, message, duration).show() }
    }
}