package com.example.tonega

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : ComponentActivity(), ToneManager.ToneStateListener {
    private lateinit var toggleButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        toggleButton = findViewById(R.id.toggleButton)
        
        toggleButton.setOnClickListener {
            if (ToneManager.isPlaying()) {
                ToneManager.stopTone()
            } else {
                ToneManager.startTone()
            }
        }
        
        ToneManager.addListener(this)
        ToneManager.startTone()
    }
    
    override fun onToneStateChanged(isPlaying: Boolean) {
        runOnUiThread {
            updateButton(isPlaying)
        }
    }

    private fun updateButton(isPlaying: Boolean) {
        if (isPlaying) {
            toggleButton.text = "STOP"
            toggleButton.icon = ContextCompat.getDrawable(this, R.drawable.ic_stop)
            toggleButton.backgroundTintList = ContextCompat.getColorStateList(this, com.google.android.material.R.color.material_dynamic_primary80)
        } else {
            toggleButton.text = "START"
            toggleButton.icon = ContextCompat.getDrawable(this, R.drawable.ic_play_arrow)
            toggleButton.backgroundTintList = ContextCompat.getColorStateList(this, com.google.android.material.R.color.material_dynamic_neutral20)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ToneManager.removeListener(this)
        ToneManager.stopTone()
    }
}