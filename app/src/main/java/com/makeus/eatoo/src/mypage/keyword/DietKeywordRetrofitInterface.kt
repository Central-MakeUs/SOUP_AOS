package com.makeus.eatoo.src.mypage.keyword

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordReq
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordResponse
import com.makeus.eatoo.src.mypage.keyword.model.PostUserKeywordReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DietKeywordRetrofitInterface {
    @GET("/app/users/{userIdx}/keyword")
    fun getDietKeyword(
        @Path("userIdx") userIdx : Int
    ): Call<DietKeywordResponse>

    @POST("/app/users/{userIdx}/keyword")
    fun postDietKeyword(
        @Path("userIdx") userIdx : Int,
        @Body postUserKeywordReq: PostUserKeywordReq
    ): Call<BaseResponse>
}