package com.makeus.eatoo.src.home.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityNotificationBinding
import com.makeus.eatoo.src.home.notification.adapter.NotificationRVAdapter
import com.makeus.eatoo.src.home.notification.model.NotificationResponse
import com.makeus.eatoo.util.getUserIdx

class NotificationActivity
    : BaseActivity<ActivityNotificationBinding>(ActivityNotificationBinding::inflate),
NotificationView{

    lateinit var notiAdapter : NotificationRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getNotification()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun getNotification() {
        showLoadingDialog(this)
        NotificationService(this).tryGetNotification(getUserIdx())
    }

    override fun onGetNotificationSuccess(response: NotificationResponse) {
        dismissLoadingDialog()
        Log.d("notificationActivity", response.toString())
        notiAdapter = NotificationRVAdapter(this, response.result)
        binding.rvNotification.apply {
            adapter = notiAdapter
            layoutManager = LinearLayoutManager(this@NotificationActivity)
        }
    }

    override fun onGetNotificationFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}