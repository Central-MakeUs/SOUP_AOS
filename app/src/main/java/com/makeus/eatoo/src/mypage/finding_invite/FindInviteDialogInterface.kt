package com.makeus.eatoo.src.mypage.finding_invite

import com.makeus.eatoo.src.mypage.finding_invite.model.FindGroupRequest
import com.makeus.eatoo.src.mypage.finding_invite.model.FindGroupResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface FindInviteDialogInterface {

    @POST("app/groups/{userIdx}/enter")
    fun postGroupParticipate(@Body body : FindGroupRequest, @Path("userIdx") userIdx : Int ): Call<FindGroupResponse>
}