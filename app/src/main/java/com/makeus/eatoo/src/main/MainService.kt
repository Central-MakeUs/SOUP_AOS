package com.makeus.eatoo.src.main

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.main.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService (val view : MainActivityView) {

    fun tryGetUserData(userIdx : Int ) {
        val userinerface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        userinerface.getUser(userIdx).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                view.onGetUserDateSuccess(response.body() as UserResponse)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                view.onGetUserDateFail(t.message ?: "통신 오류")
            }
        })
    }

}