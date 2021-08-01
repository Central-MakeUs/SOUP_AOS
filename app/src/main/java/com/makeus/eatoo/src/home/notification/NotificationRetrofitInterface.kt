package com.makeus.eatoo.src.home.notification

import com.makeus.eatoo.src.home.notification.model.NotificationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationRetrofitInterface {
    @GET("/app/users/{userIdx}/notice-list")
    fun getNotification(
        @Path("userIdx") userIdx : Int
    ) : Call<NotificationResponse>
}