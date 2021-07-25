package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate

import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model.GroupMateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FindMateRetrofitInterface {

        @GET("/app/mates/{userIdx}")
    fun getFindMate(
        @Path ("userIdx") userIdx : Int,@Query("status") status: Int
    ) : Call<GroupMateResponse>

}