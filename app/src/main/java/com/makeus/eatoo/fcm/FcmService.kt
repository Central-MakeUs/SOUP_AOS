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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.makeus.eatoo.R
import com.makeus.eatoo.src.home.group.GroupActivity

class FcmService : FirebaseMessagingService(), FcmView {

    companion object {
        private const val CHANNEL_NAME = "메이트 알림"
        private const val CHANNEL_DESCRIPTION = "메이트 매칭 알림 채널"
        private const val CHANNEL_ID = "Eatoo_notification_channel"
    }


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        //토큰이 갱신될 때 마다 서버에 토큰을 보내준다.
        FcmRetrofitService(this).tryPostFcmToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        //fcm에서 message 를 수신할 때마다 호출된다.
        createNotificationChannel()

        val title = "메이트 확정"
        val message = remoteMessage.data.getValue("data")

        NotificationManagerCompat.from(this)
            .notify(0, createNotification(title, message))
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

    private fun createNotification(title : String, message : String?): Notification {

        val intent = Intent(this, GroupActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_eatoo)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(this, R.color.main_orange))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        return notificationBuilder.build()
    }

    override fun onPostFcmSuccess() {
    }

    override fun onPostFcmFail(message: String?) {
    }
}