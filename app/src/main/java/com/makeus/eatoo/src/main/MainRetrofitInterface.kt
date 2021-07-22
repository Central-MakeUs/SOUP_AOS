package com.makeus.eatoo.src.main

import com.makeus.eatoo.src.main.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRetrofitInterface {

    @GET("/app/users/{userIdx}/profile")
    fun getUser(
        @Path ("userIdx") userIdx : Int
    )
    : Call<UserResponse>

}