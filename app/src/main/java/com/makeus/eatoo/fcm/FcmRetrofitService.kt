package com.makeus.eatoo.fcm

import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.fcm.model.FcmTokenRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FcmRetrofitService (val view : FcmView){

    fun tryPostFcmToken(token : String) {
        val fcmRetrofitInterface = ApplicationClass.sRetrofit.create(
            FcmRetrofitInterface::class.java)
        fcmRetrofitInterface.postFcmToken(FcmTokenRequest(token)).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                response.body()?.let {
                    if(it.isSuccess) view.onPostFcmSuccess()
                    else view.onPostFcmFail(it.message)
                }

            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostFcmFail(t.message?: ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }
}