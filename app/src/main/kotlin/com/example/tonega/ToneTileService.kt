package com.example.tonega

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class ToneTileService : TileService(), ToneManager.ToneStateListener {
    
    override fun onStartListening() {
        super.onStartListening()
        ToneManager.addListener(this)
        updateTile()
    }
    
    override fun onStopListening() {
        super.onStopListening()
        ToneManager.removeListener(this)
    }
    
    override fun onToneStateChanged(isPlaying: Boolean) {
        updateTile()
    }
    
    override fun onClick() {
        super.onClick()
        
        if (ToneManager.isPlaying()) {
            ToneManager.stopTone()
        } else {
            ToneManager.startTone()
        }
    }
    
    private fun updateTile() {
        val tile = qsTile ?: return
        
        if (ToneManager.isPlaying()) {
            tile.state = Tile.STATE_ACTIVE
            tile.label = "852Hz Tone ON"
        } else {
            tile.state = Tile.STATE_INACTIVE
            tile.label = "852Hz Tone OFF"
        }
        
        tile.updateTile()
    }
}