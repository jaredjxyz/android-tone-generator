package com.example.tonega

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
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
            animateButtonPress()
            if (ToneManager.isPlaying()) {
                ToneManager.stopTone()
            } else {
                ToneManager.startTone()
            }
        }
        
        ToneManager.addListener(this)
        updateButton(ToneManager.isPlaying())
    }
    
    override fun onToneStateChanged(isPlaying: Boolean) {
        runOnUiThread {
            updateButton(isPlaying)
        }
    }

    private fun animateButtonPress() {
        val scaleDownX = ObjectAnimator.ofFloat(toggleButton, "scaleX", 1f, 0.9f)
        val scaleDownY = ObjectAnimator.ofFloat(toggleButton, "scaleY", 1f, 0.9f)
        val scaleUpX = ObjectAnimator.ofFloat(toggleButton, "scaleX", 0.9f, 1f)
        val scaleUpY = ObjectAnimator.ofFloat(toggleButton, "scaleY", 0.9f, 1f)
        
        scaleDownX.duration = 100
        scaleDownY.duration = 100
        scaleUpX.duration = 100
        scaleUpY.duration = 100
        
        val scaleDown = AnimatorSet().apply {
            playTogether(scaleDownX, scaleDownY)
            interpolator = AccelerateDecelerateInterpolator()
        }
        
        val scaleUp = AnimatorSet().apply {
            playTogether(scaleUpX, scaleUpY)
            interpolator = AccelerateDecelerateInterpolator()
        }
        
        val animatorSet = AnimatorSet().apply {
            playSequentially(scaleDown, scaleUp)
        }
        
        animatorSet.start()
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