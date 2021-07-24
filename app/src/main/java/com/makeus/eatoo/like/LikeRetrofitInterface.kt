package com.makeus.eatoo.like

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.like.model.LikeResponse
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeRetrofitInterface {
    @POST("/app/stores/{userIdx}/{storeIdx}")
    fun postLike(
        @Path("userIdx") userIdx : Int,
        @Path("storeIdx") storeIdx : Int
    ) : Call<LikeResponse>

    @PATCH ("/app/stores/delete/{userIdx}/{storeIdx}")
    fun patchLike (
        @Path("userIdx") userIdx : Int,
        @Path("storeIdx") storeIdx : Int
    ) : Call<BaseResponse>
}