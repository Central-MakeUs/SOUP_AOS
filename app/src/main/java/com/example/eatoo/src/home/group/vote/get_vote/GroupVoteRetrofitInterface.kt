package com.example.eatoo.src.home.group.vote.get_vote

import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupVoteRetrofitInterface {
    @GET("/app/groups/{userIdx}/{groupIdx}/votes")
    fun getGroupVote(
        @Path("userIdx") userIdx : Int,
        @Path("groupIdx") groupIdx : Int
    ) : Call<GroupVoteResponse>
}