package com.bqliang.baddyrank.sync.initializers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ForegroundInfo
import androidx.work.NetworkType

const val SYNC_TOPIC = "sync"
private const val SYNC_NOTIFICATION_ID = 0
private const val SYNC_NOTIFICATION_CHANNEL_ID = "SyncNotificationChannel"
private const val SYNC_NOTIFICATION_CHANNEL_NAME = "Sync"
private const val SYNC_NOTIFICATION_CHANNEL_DESCRIPTION = "Background tasks for BaddyRank"
private const val SYNC_NOTIFICATION_TITLE = "BaddyRank"

// All sync work needs an internet connectionS
val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

/**
 * Foreground information for sync on lower API levels when sync workers are being
 * run with a foreground service
 */
fun Context.syncForegroundInfo() = ForegroundInfo(
    SYNC_NOTIFICATION_ID,
    syncWorkNotification(),
)

/**
 * Notification displayed on lower API levels when sync workers are being
 * run with a foreground service
 */
private fun Context.syncWorkNotification(): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            SYNC_NOTIFICATION_CHANNEL_ID,
            SYNC_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = SYNC_NOTIFICATION_CHANNEL_DESCRIPTION
        }
        // Register the channel with the system
        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(
        this,
        SYNC_NOTIFICATION_CHANNEL_ID,
    )
        // .setSmallIcon(
        //
        // )
        .setContentTitle(SYNC_NOTIFICATION_TITLE)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
