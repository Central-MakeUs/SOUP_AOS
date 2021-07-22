package com.makeus.eatoo.src.home.group.groupmatesuggestion

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateCreateService (val view : Mate_Suggestion_ActivityView){


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