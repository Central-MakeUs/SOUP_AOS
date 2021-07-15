package com.example.eatoo.src.home.group.vote.create_vote

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.group.vote.create_vote.model.CreateVoteRequest
import com.example.eatoo.src.home.group.vote.create_vote.model.CreateVoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateVoteService (val view : CreateVoteView){

    fun tryPostVote(userIdx: Int, storeIdx : Int, createVoteRequest: CreateVoteRequest) {
        val createVoteRetrofitInterface = ApplicationClass.sRetrofit.create(
            CreateVoteRetrofitInterface::class.java
        )
        createVoteRetrofitInterface.postVote(userIdx, storeIdx, createVoteRequest)
            .enqueue(object :
                Callback<CreateVoteResponse> {
                override fun onResponse(
                    call: Call<CreateVoteResponse>,
                    response: Response<CreateVoteResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onPostCreateVoteSuccess()
                        else view.onPostCreateVoteFail(it.message)
                    }
                }

                override fun onFailure(call: Call<CreateVoteResponse>, t: Throwable) {
                    view.onPostCreateVoteFail(t.message)
                }

            })
    }
}