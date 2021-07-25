package com.makeus.eatoo.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_NAME = "메이트 알림"
        private const val CHANNEL_DESCRIPTION = "메이트 매칭 알림 채널"
        private const val CHANNEL_ID = "Eatoo_notification_channel"
    }


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        //토큰이 갱신될 때 마다 서버에 토큰을 보내준다.
//        FcmRetrofitService(null, this).tryRegisterNewFcmToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        //fcm에서 message 를 수신할 때마다 호출된다.
        createNotificationChannel()

        val title = remoteMessage.notification?.title
        val message = remoteMessage.notification?.body
        val titleData = remoteMessage.data.toString()
        Log.d("fcm", remoteMessage.toString())

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(titleData)
            .setContentText(titleData)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)

        NotificationManagerCompat.from(this)
            .notify(1, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

//    private fun createNotification(title : String?, message : String?): Notification {
//
//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(
//            this, 0, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        return notificationBuilder.build()
//    }
}