package com.example.eatoo.single_status


import com.example.eatoo.config.BaseResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SingleRetrofitInterface {

    @PATCH ("/app/users/{userIdx}/singleStatus")
    fun patchSingleStatus(@Path ("userIdx") userIdx : Int)
    : Call<SingleResultResponse>

}