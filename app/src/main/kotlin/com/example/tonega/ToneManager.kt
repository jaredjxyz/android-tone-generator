package com.example.tonega

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.math.*

object ToneManager {
    private var audioTrack: AudioTrack? = null
    private var isPlaying = false
    private var toneThread: Thread? = null
    private val listeners = mutableListOf<ToneStateListener>()
    
    interface ToneStateListener {
        fun onToneStateChanged(isPlaying: Boolean)
    }
    
    fun addListener(listener: ToneStateListener) {
        listeners.add(listener)
    }
    
    fun removeListener(listener: ToneStateListener) {
        listeners.remove(listener)
    }
    
    private fun notifyListeners() {
        listeners.forEach { it.onToneStateChanged(isPlaying) }
    }
    
    fun isPlaying(): Boolean = isPlaying
    
    fun startTone() {
        if (isPlaying) return
        
        val sampleRate = 44100
        val frequency = 852

        toneThread = Thread {
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM
            )

            audioTrack?.play()
            isPlaying = true
            notifyListeners()

            val samplesPerBuffer = bufferSize / 2
            val buffer = ShortArray(samplesPerBuffer)

            var phase = 0.0
            val phaseIncrement = 2 * PI * frequency / sampleRate

            while (isPlaying && !Thread.currentThread().isInterrupted) {
                for (i in 0 until samplesPerBuffer) {
                    buffer[i] = (Short.MAX_VALUE * sin(phase)).toInt().toShort()
                    phase += phaseIncrement
                    if (phase >= 2 * PI) {
                        phase -= 2 * PI
                    }
                }
                audioTrack?.write(buffer, 0, samplesPerBuffer)
            }
        }

        toneThread?.start()
    }
    
    fun stopTone() {
        isPlaying = false
        notifyListeners()
        toneThread?.interrupt()
        audioTrack?.apply {
            stop()
            release()
        }
        audioTrack = null
    }
    
    fun updateWidgets(context: android.content.Context) {
        ToneWidgetProvider.updateAllWidgets(context)
    }
}