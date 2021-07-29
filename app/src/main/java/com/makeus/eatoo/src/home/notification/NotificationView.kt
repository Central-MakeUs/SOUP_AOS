package com.makeus.eatoo.src.home.notification

import com.makeus.eatoo.src.home.notification.model.NotificationResponse

interface NotificationView {
    fun onGetNotificationSuccess(response : NotificationResponse)
    fun onGetNotificationFail(message : String?)
}