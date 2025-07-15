package com.example.tonega

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class ToneWidgetProvider : AppWidgetProvider(), ToneManager.ToneStateListener {

    companion object {
        const val ACTION_TOGGLE_TONE = "com.example.tonega.ACTION_TOGGLE_TONE"
        
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, ToneWidgetProvider::class.java)
            )
            val intent = Intent(context, ToneWidgetProvider::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
            }
            context.sendBroadcast(intent)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        
        when (intent.action) {
            ACTION_TOGGLE_TONE -> {
                if (ToneManager.isPlaying()) {
                    ToneManager.stopTone()
                } else {
                    ToneManager.startTone()
                }
                updateAllWidgets(context)
            }
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        ToneManager.addListener(this)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        ToneManager.removeListener(this)
    }

    override fun onToneStateChanged(isPlaying: Boolean) {
        // Widget updates will be handled through updateAllWidgets calls
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_tone_toggle)
        
        // Set icon based on tone state
        val iconRes = if (ToneManager.isPlaying()) {
            R.drawable.ic_stop
        } else {
            R.drawable.ic_play_arrow
        }
        views.setImageViewResource(R.id.widget_toggle_button, iconRes)
        
        // Create pending intent for button click
        val toggleIntent = Intent(context, ToneWidgetProvider::class.java).apply {
            action = ACTION_TOGGLE_TONE
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, toggleIntent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widget_toggle_button, pendingIntent)
        
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}