package com.example.eatoo.src.home.group.main

import com.example.eatoo.src.home.group.main.model.GroupMainResponse
import com.example.eatoo.src.home.group.main.model.MateAttendResponse
import com.example.eatoo.src.mypage.finding_invite.model.FindGroupRequest
import com.example.eatoo.src.mypage.finding_invite.model.FindGroupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupMainRetrofitInterface {

    @GET("app/groups/{userIdx}/{groupIdx}/main")
    fun getGroupMembers(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int
    ) : Call<GroupMainResponse>


    @POST("/app/mates/{userIdx}/{mateIdx}")
    fun postMateAttend(@Path ("userIdx") userIdx : Int, @Path ("mateIdx") mateIdx : Int ): Call<MateAttendResponse>
}