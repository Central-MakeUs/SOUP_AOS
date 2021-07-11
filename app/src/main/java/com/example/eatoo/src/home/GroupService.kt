package com.example.eatoo.src.home

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.model.GroupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupService (val view : GroupView) {

    fun tryGetData(userIdx : Int ) {
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
}