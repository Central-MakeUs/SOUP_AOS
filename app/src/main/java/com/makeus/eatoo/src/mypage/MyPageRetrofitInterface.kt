package com.makeus.eatoo.src.mypage

import com.makeus.eatoo.src.mypage.model.AccountDeleteResponse
import com.makeus.eatoo.src.mypage.model.MyPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MyPageRetrofitInterface {

    @GET("/app/users/{userIdx}")
    fun getMyPage(
        @Path("userIdx") userIdx : Int
    ) : Call<MyPageResponse>

    @PATCH("/app/users/delete/{userIdx}")
    fun deleteAccount(
        @Path("userIdx") userIdx : Int
    ) : Call<AccountDeleteResponse>

}