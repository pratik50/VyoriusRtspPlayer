package com.pratik.vyoriusrtspplayer.vlc

import android.content.Context
import com.pratik.vyoriusrtspplayer.utils.VlcUtils
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

class VlcPlayer(private val context: Context, private val videoLayout: VLCVideoLayout) {

    private val libVLC: LibVLC
    private val mediaPlayer: MediaPlayer

    init {
        val options = arrayListOf(
            "--rtsp-tcp",
            "--avcodec-hw=none",
            "--network-caching=300",
            "--no-drop-late-frames",
            "--no-skip-frames",
            "--file-caching=300"
        )
        libVLC = VlcUtils.createLibVLC(context, options)
        mediaPlayer = MediaPlayer(libVLC)
        mediaPlayer.attachViews(videoLayout, null, true, false)
        mediaPlayer.setEventListener(VlcUtils.getDefaultEventListener(context))
    }

    fun startPlayback(url: String) {
        val media = VlcUtils.createMedia(libVLC, url)
        media.addOption(":network-caching=300")
        media.addOption(":file-caching=300")

        mediaPlayer.media = media
        media.release()
        mediaPlayer.play()
    }

    fun release() {
        mediaPlayer.stop()
        mediaPlayer.detachViews()
        libVLC.release()
    }
}