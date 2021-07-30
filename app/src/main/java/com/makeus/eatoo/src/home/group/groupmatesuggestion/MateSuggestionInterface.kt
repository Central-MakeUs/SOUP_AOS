package com.makeus.eatoo.src.home.group.groupmatesuggestion

import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface MateSuggestionInterface {

    @POST("/app/mates/{userIdx}")
    fun postCreateMate(@Body body : CreateMateRequest,@Path("userIdx") userIdx : Int): Call<CreateMateResponse>
}