package com.example.eatoo.src.home.group.vote.get_vote

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse
import com.example.eatoo.src.home.group.vote.get_vote.model.VoteDetailResponse
import com.example.eatoo.src.home.group.vote.get_vote.model.VotedRequest
import com.example.eatoo.src.home.group.vote.get_vote.model.VotedResponse
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

    fun tryGetVoteDetail(userIdx: Int, groupIdx : Int, voteIdx : Int) {
        val groupVoteRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupVoteRetrofitInterface::class.java
        )
        groupVoteRetrofitInterface.getGroupVoteDetail(userIdx, groupIdx, voteIdx)
            .enqueue(object :
                Callback<VoteDetailResponse> {
                override fun onResponse(
                    call: Call<VoteDetailResponse>,
                    response: Response<VoteDetailResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetVoteDetailSuccess(response.body() as VoteDetailResponse)
                        else view.onGetVoteDetailFail(it.message)
                    }
                }

                override fun onFailure(call: Call<VoteDetailResponse>, t: Throwable) {
                    view.onGetVoteDetailFail(t.message)
                }

            })
    }

    fun tryPostVote(userIdx: Int, groupIdx : Int, voteIdx : Int, votedRequest: VotedRequest) {
        val groupVoteRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupVoteRetrofitInterface::class.java
        )
        groupVoteRetrofitInterface.postVote(userIdx, groupIdx, voteIdx, votedRequest)
            .enqueue(object :
                Callback<VotedResponse> {
                override fun onResponse(
                    call: Call<VotedResponse>,
                    response: Response<VotedResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onPostVotedSuccess(response.body() as VotedResponse)
                        else view.onPostVotedFail(it.message)
                    }
                }

                override fun onFailure(call: Call<VotedResponse>, t: Throwable) {
                    view.onPostVotedFail(t.message)
                }

            })
    }
}