package com.makeus.eatoo.src.home.group.member

import com.makeus.eatoo.src.home.group.member.model.GroupMemberDetailResponse
import com.makeus.eatoo.src.home.group.member.model.GroupMemberResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupMemberRetrofitInterface {

    @GET("app/groups/{userIdx}/{groupIdx}/members")
    fun getGroupMembers(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int
    ) : Call<GroupMemberResponse>

    @GET("app/groups/{userIdx1}/{userIdx2}")
    fun getGroupMemberDetail(
        @Path ("userIdx1") userIdx1 : Int,
        @Path ("userIdx2") userIdx2 : Int
    ) : Call<GroupMemberDetailResponse>
}