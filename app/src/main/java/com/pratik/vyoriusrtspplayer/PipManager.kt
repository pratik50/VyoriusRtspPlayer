package com.pratik.vyoriusrtspplayer

import android.app.Activity
import android.app.PictureInPictureParams
import android.os.Build
import android.view.View
import android.widget.Toast
import com.pratik.vyoriusrtspplayer.databinding.ActivityPlayerBinding
import com.pratik.vyoriusrtspplayer.utils.UiUtils

object PipManager {

    fun enterPiPMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.enterPictureInPictureMode(PictureInPictureParams.Builder().build())
        } else {
            Toast.makeText(activity, "PiP not supported on this device", Toast.LENGTH_SHORT).show()
        }
    }

    fun toggleUIVisibility(binding: ActivityPlayerBinding, show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        binding.btnRecord.visibility = visibility
        binding.rtspText.visibility = visibility
        binding.btnMiniPlayer.visibility = visibility

        UiUtils.makeActivityFullScreen(binding.root.context as Activity)
    }

    fun shouldFinishOnStop(isInPiPMode: Boolean, isChangingConfigurations: Boolean): Boolean {
        return isInPiPMode && !isChangingConfigurations
    }
}