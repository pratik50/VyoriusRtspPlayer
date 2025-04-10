package com.pratik.vyoriusrtspplayer

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pratik.vyoriusrtspplayer.databinding.ActivityPlayerBinding
import com.pratik.vyoriusrtspplayer.utils.UiUtils
import com.pratik.vyoriusrtspplayer.vlc.VlcPlayer
import com.pratik.vyoriusrtspplayer.vlc.VlcRecorder

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var vlcPlayer: VlcPlayer
    private lateinit var vlcRecorder: VlcRecorder
    private var rtspUrl: String = ""
    private var isRecording = false
    private var isInPiPMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up full-screen mode
        UiUtils.makeActivityFullScreen(this)

        // Get and validate RTSP URL from intent
        rtspUrl = intent.getStringExtra("RTSP_URL")!!
        binding.rtspText.text = rtspUrl

        // Check storage permissions
        PermissionManager.checkStoragePermission(this)

        // Initialize VLC components
        vlcPlayer = VlcPlayer(this, binding.vlcVideoLayout)
        vlcRecorder = VlcRecorder(this)

        // Start playback
        vlcPlayer.startPlayback(rtspUrl)

        // Set up button listeners
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnMiniPlayer.setOnClickListener {
            PipManager.enterPiPMode(this)
        }

        binding.btnRecord.setOnClickListener {
            if (isRecording) {
                vlcRecorder.stopRecording()
                isRecording = false
                binding.btnRecord.setBackgroundResource(R.drawable.bg_record_idle)
            } else {
                vlcRecorder.startRecording(rtspUrl)
                isRecording = true
                binding.btnRecord.setImageResource(R.drawable.ic_record_active)
                binding.btnRecord.setBackgroundResource(R.drawable.bg_record_active)
                UiUtils.animateButton(binding.btnRecord)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vlcPlayer.release()
        vlcRecorder.release()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        PipManager.enterPiPMode(this)
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        isInPiPMode = isInPictureInPictureMode
        PipManager.toggleUIVisibility(binding, !isInPictureInPictureMode)
    }

    override fun onStop() {
        super.onStop()
        if (PipManager.shouldFinishOnStop(isInPiPMode, isChangingConfigurations)) {
            finishAndRemoveTask()
        }
    }

}