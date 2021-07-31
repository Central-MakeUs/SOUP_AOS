package com.makeus.eatoo.src.suggestion

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.suggestion.model.SuggestionMateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySuggestionService (val view : MySuggestionView) {


    fun tryGetMateData(userIdx : Int ) {
        val mateinerface = ApplicationClass.sRetrofit.create(MySuggestionRetrofitInterface::class.java)
        mateinerface.getMate(userIdx).enqueue(object : Callback<SuggestionMateResponse> {
            override fun onResponse(call: Call<SuggestionMateResponse>, response: Response<SuggestionMateResponse>) {
                view.onGetMateSuccess(response.body() as SuggestionMateResponse)
            }

            override fun onFailure(call: Call<SuggestionMateResponse>, t: Throwable) {
                view.onGetMateFail(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteMate(userIdx : Int , mateIdx : Int) {
        val mateInterface= ApplicationClass.sRetrofit.create(MySuggestionRetrofitInterface::class.java)
        mateInterface.deleteMate(userIdx, mateIdx).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                response.body()?.let {
                    if(it.isSuccess) view.onDeleteSuggestSuccess(response.body() as BaseResponse)
                    else view.onDeleteSuggestFail(it.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteSuggestFail(t.message ?: "통신 오류")
            }
        })
    }
}