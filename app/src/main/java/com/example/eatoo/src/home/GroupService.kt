package com.example.eatoo.src.home

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.MateResponse
import com.example.eatoo.src.home.model.MateResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupService (val view : GroupView) {

    fun tryGetGroupData(userIdx : Int ) {
        val groupinerface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupinerface.getGroup(userIdx).enqueue(object : Callback<GroupResponse> {
            override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {
                view.onGetGroupSuccess(response.body() as GroupResponse)
            }

            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                view.onGetGroupFail(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetMateData(userIdx : Int ) {
        val groupinerface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupinerface.getMate(userIdx).enqueue(object : Callback<MateResponse> {
            override fun onResponse(call: Call<MateResponse>, response: Response<MateResponse>) {
                view.onGetMateSuccess(response.body() as MateResponse)
            }

            override fun onFailure(call: Call<MateResponse>, t: Throwable) {
                view.onGetMateFail(t.message ?: "통신 오류")
            }
        })
    }
}