package com.makeus.eatoo.src.home.group.groupmatesuggestion

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.GroupRetrofitInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import com.makeus.eatoo.src.home.model.GroupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateCreateService (val view : Mate_Suggestion_ActivityView){

    fun tryGetGroupData(userIdx : Int ) {
        val groupInterface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupInterface.getGroup(userIdx).enqueue(object : Callback<GroupResponse> {
            override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {
                response.body()?.let {
                    if(it.isSuccess)  view.onGetGroupSuccess(response.body() as GroupResponse)
                    else  view.onGetGroupFail(it.message?: "통신 오류")
                }

            }

            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                view.onGetGroupFail(t.message ?: "통신 오류")
            }
        })
    }


    fun postCreateMate(postCreateMateRequest: CreateMateRequest,userIdx : Int){
        val createmateinerface = ApplicationClass.sRetrofit.create(MateCreateInterface::class.java)
        createmateinerface.postCreateMate(postCreateMateRequest,userIdx).enqueue(object :
                Callback<CreateMateResponse> {
            override fun onResponse(call: Call<CreateMateResponse>, response: Response<CreateMateResponse>) {
                view.onPostMateCreateSuccess(response.body() as CreateMateResponse)
            }

            override fun onFailure(call: Call<CreateMateResponse>, t: Throwable) {
                view.onPostMateCreateFailure(t.message ?: "통신 오류")
            }
        })
    }
}