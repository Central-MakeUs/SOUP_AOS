package com.makeus.eatoo.src.suggestion


import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.suggestion.model.SuggestionMateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MySuggestionRetrofitInterface {

    @GET("/app/homes/{userIdx}/my-mates")
    fun getMate(
        @Path ("userIdx") userIdx : Int
    ): Call<SuggestionMateResponse>

    @PATCH("/app/mates/delete/{userIdx}/{mateIdx}")
    fun deleteMate(
        @Path ("userIdx") userIdx : Int,
        @Path ("mateIdx") mateIdx : Int
    ): Call<BaseResponse>
}