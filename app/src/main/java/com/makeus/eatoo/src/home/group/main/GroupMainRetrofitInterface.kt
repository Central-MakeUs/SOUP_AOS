package com.makeus.eatoo.src.home.group.main

import com.makeus.eatoo.src.home.group.main.model.GroupMainResponse
import com.makeus.eatoo.src.home.group.main.model.MateAttendResponse
import retrofit2.Call
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