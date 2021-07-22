package com.example.eatoo.src.home.group.groupmatesuggestion

import com.example.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.example.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface MateCreateInterface {

    @POST("/app/mates/{userIdx}")
    fun postCreateMate(@Body body : CreateMateRequest,@Path("userIdx") userIdx : Int): Call<CreateMateResponse>
}