package com.makeus.eatoo.src.home.notification

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.notification.model.NotificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationService(val view : NotificationView) {

    fun tryGetNotification(userIdx : Int) {
        val notificationRetrofitInterface = ApplicationClass.sRetrofit.create(
            NotificationRetrofitInterface::class.java
        )
        notificationRetrofitInterface.getNotification(userIdx)
            .enqueue(object :
                Callback<NotificationResponse> {
                override fun onResponse(
                    call: Call<NotificationResponse>,
                    response: Response<NotificationResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetNotificationSuccess(response.body() as NotificationResponse)
                        else view.onGetNotificationFail(it.message)
                    }
                }
                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                    view.onGetNotificationFail(t.message)
                }

            })
    }
}