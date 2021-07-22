package com.example.eatoo.src.home.group.vote.create_vote

import com.example.eatoo.src.home.group.vote.create_vote.model.CreateVoteRequest
import com.example.eatoo.src.home.group.vote.create_vote.model.CreateVoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CreateVoteRetrofitInterface {

    @POST("/app/votes/{userIdx}/{groupIdx}")
    fun postVote(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int,
        @Body createVoteRequest: CreateVoteRequest
    ) : Call<CreateVoteResponse>
}