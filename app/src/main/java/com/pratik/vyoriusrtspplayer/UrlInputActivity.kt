package com.pratik.vyoriusrtspplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pratik.vyoriusrtspplayer.databinding.ActivityUrlInputBinding

class UrlInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUrlInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUrlInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()

    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            val url = binding.etRtspUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                navigateToPlayer(url)
            } else {
                Toast.makeText(this, "Enter a valid RTSP URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToPlayer(url: String) {
        val intent = Intent(this, PlayerActivity::class.java).apply {
            putExtra("RTSP_URL", url)
        }
        startActivity(intent)
        binding.etRtspUrl.text.clear()
    }
}