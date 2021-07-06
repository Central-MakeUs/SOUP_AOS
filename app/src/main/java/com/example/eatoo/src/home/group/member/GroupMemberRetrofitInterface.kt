package com.example.eatoo.src.home.group.member

import com.example.eatoo.src.home.group.member.model.GroupMemberResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupMemberRetrofitInterface {

    @GET("app/groups/{userIdx}/{groupIdx}/members")
    fun getGroupMembers(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int
    ) : Call<GroupMemberResponse>
}