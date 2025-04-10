package com.pratik.vyoriusrtspplayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings

object PermissionManager {

    private const val PREFS_NAME = "vyorius_prefs"
    private const val KEY_ALREADY_PROMPTED = "already_prompted"

    fun checkStoragePermission(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val alreadyPrompted = prefs.getBoolean(KEY_ALREADY_PROMPTED, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
            !Environment.isExternalStorageManager() &&
            !alreadyPrompted
        ) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:${context.packageName}")
            context.startActivity(intent)
            prefs.edit().putBoolean(KEY_ALREADY_PROMPTED, true).apply()
        }
    }
}