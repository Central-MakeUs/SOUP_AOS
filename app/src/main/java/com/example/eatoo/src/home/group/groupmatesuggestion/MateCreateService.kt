package com.example.eatoo.src.home.group.groupmatesuggestion

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.example.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateCreateService (val view : Mate_Suggestion_ActivityView){




    fun postCreateMate(postCreateMateRequest: CreateMateRequest){
        val createmateinerface = ApplicationClass.sRetrofit.create(MateCreateInterface::class.java)
        createmateinerface.postCreateMate(postCreateMateRequest).enqueue(object :
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