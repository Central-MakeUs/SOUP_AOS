package com.example.eatoo.src.home.group.main

import com.example.eatoo.src.home.group.main.model.GroupMainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupMainRetrofitInterface {

    @GET("app/groups/{userIdx}/{groupIdx}/main")
    fun getGroupMembers(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int
    ) : Call<GroupMainResponse>
}