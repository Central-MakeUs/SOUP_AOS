package com.example.eatoo.src.home.group.vote.get_vote

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupVoteService (val view : GroupVoteView){

    fun tryGetGroupVote(userIdx: Int, groupIdx : Int) {
        val groupVoteRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupVoteRetrofitInterface::class.java
        )
        groupVoteRetrofitInterface.getGroupVote(userIdx, groupIdx)
            .enqueue(object :
                Callback<GroupVoteResponse> {
                override fun onResponse(
                    call: Call<GroupVoteResponse>,
                    response: Response<GroupVoteResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetGroupVoteSuccess(response.body() as GroupVoteResponse)
                        else view.onGetGroupVoteFail(it.message)
                    }
                }

                override fun onFailure(call: Call<GroupVoteResponse>, t: Throwable) {
                    view.onGetGroupVoteFail(t.message)
                }

            })
    }
}