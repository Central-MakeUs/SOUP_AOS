package com.makeus.eatoo.single_status


import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SingleRetrofitInterface {

    @PATCH ("/app/users/{userIdx}/singleStatus")
    fun patchSingleStatus(@Path ("userIdx") userIdx : Int)
    : Call<SingleResultResponse>

}