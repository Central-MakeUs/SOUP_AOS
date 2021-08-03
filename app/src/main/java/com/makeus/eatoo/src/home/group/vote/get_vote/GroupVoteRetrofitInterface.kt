package com.makeus.eatoo.src.home.group.vote.get_vote

import com.makeus.eatoo.src.home.group.vote.get_vote.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupVoteRetrofitInterface {
    @GET("/app/groups/{userIdx}/{groupIdx}/votes")
    fun getGroupVote(
        @Path("userIdx") userIdx : Int,
        @Path("groupIdx") groupIdx : Int
    ) : Call<GroupVoteResponse>

    @GET("/app/votes/{userIdx}/{groupIdx}/{voteIdx}")
    fun getGroupVoteDetail(
        @Path("userIdx") userIdx : Int,
        @Path("groupIdx") groupIdx : Int,
        @Path("voteIdx") voteIdx : Int
    ) : Call<VoteDetailResponse>

    @POST ("/app/votes/{userIdx}/{groupIdx}/{voteIdx}")
    fun postVote(
        @Path("userIdx") userIdx : Int,
        @Path("groupIdx") groupIdx : Int,
        @Path("voteIdx") voteIdx : Int,
        @Body votedRequest : VotedRequest
    ) : Call<VotedResponse>

    @POST ("/app/votes/{userIdx}/{voteIdx}/add-menu")
    fun postAddedVoteItem(
        @Path("userIdx") userIdx : Int,
        @Path("voteIdx") voteIdx : Int,
        @Body addedVoteItem : NewItem
    ) : Call<NewItemAddedResponse>

    @GET("/app/votes/{userIdx}/{voteIdx}/{voteMenuIdx}/members")
    fun getVotedMember(
        @Path("userIdx") userIdx : Int,
        @Path("voteIdx") voteIdx : Int,
        @Path("voteMenuIdx") voteMenuIdx : Int
    ) : Call<VotedMemberResponse>
}