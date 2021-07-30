package com.makeus.eatoo.src.home.group.groupmatesuggestion

import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.GroupRetrofitInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import com.makeus.eatoo.src.home.model.GroupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateSuggestionService (val view : MateSuggestionView){

    fun tryGetGroupData(userIdx : Int ) {
        val groupInterface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupInterface.getGroup(userIdx).enqueue(object : Callback<GroupResponse> {
            override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {
                response.body()?.let {
                    if(it.isSuccess)  view.onGetGroupSuccess(response.body() as GroupResponse)
                    else  view.onGetGroupFail(it.message?: ApplicationClass.applicationResources.getString(
                        R.string.failed_connection))
                }

            }

            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                view.onGetGroupFail(t.message ?: ApplicationClass.applicationResources.getString(
                    R.string.failed_connection))
            }
        })
    }


    fun tryPostMate(postCreateMateRequest: CreateMateRequest,userIdx : Int){
        val mateSuggestionInterface = ApplicationClass.sRetrofit.create(MateSuggestionInterface::class.java)
        mateSuggestionInterface.postCreateMate(postCreateMateRequest,userIdx).enqueue(object :
                Callback<CreateMateResponse> {
            override fun onResponse(call: Call<CreateMateResponse>, response: Response<CreateMateResponse>) {
                response.body()?.let {
                    if(it.isSuccess) view.onPostMateCreateSuccess(response.body() as CreateMateResponse)
                    else  view.onPostMateCreateFailure(it.message ?: ApplicationClass.applicationResources.getString(
                        R.string.failed_connection))
                }

            }
            override fun onFailure(call: Call<CreateMateResponse>, t: Throwable) {
                view.onPostMateCreateFailure(t.message ?: ApplicationClass.applicationResources.getString(
                    R.string.failed_connection))
            }
        })
    }
}