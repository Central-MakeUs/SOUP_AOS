package com.makeus.eatoo.src.mypage

import com.makeus.eatoo.src.mypage.model.MyPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageRetrofitInterface {

    @GET("/app/users/{userIdx}")
    fun getMyPage(
        @Path("userIdx") userIdx : Int
    ) : Call<MyPageResponse>

}