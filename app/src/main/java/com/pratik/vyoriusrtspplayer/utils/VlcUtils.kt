package com.pratik.vyoriusrtspplayer.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer

object VlcUtils {

    fun createLibVLC(context: Context, options: List<String>): LibVLC {
        return LibVLC(context, options)
    }

    fun createMedia(libVLC: LibVLC, url: String): Media {
        val media = Media(libVLC, Uri.parse(url))
        media.setHWDecoderEnabled(false, false)
        media.addOption(":rtsp-tcp")
        media.addOption(":no-drop-late-frames")
        media.addOption(":no-skip-frames")
        return media
    }

    fun getDefaultEventListener(context: Context): MediaPlayer.EventListener {
        return MediaPlayer.EventListener { event ->
            when (event.type) {
                MediaPlayer.Event.EncounteredError -> {
                    Toast.makeText(context, "Error occurred! Recheck the URL", Toast.LENGTH_LONG).show()
                    (context as? AppCompatActivity)?.finish()
                }
            }
        }
    }

    fun getRecordingEventListener(context: Context): MediaPlayer.EventListener {
        return MediaPlayer.EventListener { event ->
            when (event.type) {
                MediaPlayer.Event.Playing -> Log.d("Recorder", "Recording started playing")
                MediaPlayer.Event.EndReached -> Log.d("Recorder", "Recording stream ended")
                MediaPlayer.Event.EncounteredError -> {
                    Toast.makeText(context, "Recording failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}