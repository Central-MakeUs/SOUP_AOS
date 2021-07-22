package com.example.eatoo.src.home.group.main

import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.group.main.model.GroupMainResponse
import com.example.eatoo.src.home.group.main.model.MateAttendResponse
import com.example.eatoo.src.signinlogin.login.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateAttendService (val view : MateAttendView){
    fun tryPostMateAttend(userIdx : Int, mateIdx : Int) {
        val mateattendRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupMainRetrofitInterface::class.java)
        mateattendRetrofitInterface.postMateAttend(userIdx = userIdx, mateIdx = mateIdx).enqueue(object :
            Callback<MateAttendResponse> {
            override fun onResponse(call: Call<MateAttendResponse>, response: Response<MateAttendResponse>) {
                view.onGetMateAttendSuccess(response.body() as MateAttendResponse)
            }

            override fun onFailure(call: Call<MateAttendResponse>, t: Throwable) {
                view.onGetMateAttendFail(t.message ?: "통신 오류")
            }
        })
    }
}