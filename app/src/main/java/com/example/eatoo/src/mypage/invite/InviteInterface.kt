package com.example.eatoo.src.mypage.invite

import com.example.eatoo.src.main.model.UserResponse
import com.example.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.example.eatoo.src.mypage.invite.model.InviteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InviteInterface {

    @GET("/app/users/{userIdx}/invitation")
    fun getInviteGroup(
        @Path ("userIdx") userIdx : Int
    )
    : Call<InviteResponse>


    @GET("/app/users/{userIdx}/{groupIdx}/invitation")
    fun getInviteCode(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int
    )
            : Call<InviteCodeResponse>
}