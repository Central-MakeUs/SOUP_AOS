package com.makeus.eatoo.fcm

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.fcm.model.FcmTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmRetrofitInterface {
    @POST("/app/users/token")
    fun postFcmToken(
        @Body fcmTokenRequest : FcmTokenRequest
    ): Call<BaseResponse>
}