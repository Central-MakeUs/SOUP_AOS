package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model.GroupMateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindMateService (val view : FindMateView) {

    fun tryGetFindMateData(userIdx : Int ,groupIdx : Int , status : Int) {
        val mateinerface = ApplicationClass.sRetrofit.create(FindMateRetrofitInterface::class.java)
        mateinerface.getFindMate(userIdx,groupIdx,status).enqueue(object : Callback<GroupMateResponse> {
            override fun onResponse(call: Call<GroupMateResponse>, response: Response<GroupMateResponse>) {
                view.onGetFindMateSuccess(response.body() as GroupMateResponse)
            }

            override fun onFailure(call: Call<GroupMateResponse>, t: Throwable) {
                view.onGetFindMateFail(t.message ?: "통신 오류")
            }
        })
    }





}