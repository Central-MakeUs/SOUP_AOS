package com.example.eatoo.src.suggestion


import com.example.eatoo.src.suggestion.model.SuggestionMateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MySuggestionRetrofitInterface {

    @GET("/app/homes/{userIdx}/my-mates")
    fun getMate(
        @Path ("userIdx") userIdx : Int
    ): Call<SuggestionMateResponse>
}