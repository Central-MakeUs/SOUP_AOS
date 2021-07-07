package com.example.eatoo.src.home

import com.example.eatoo.src.home.model.GroupResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupRetrofitInterface {

    @GET("/app/users/{userIdx}/invitation") //여기 아님. 메인홈 그룹조회는 아직 구현 안 됨.
    fun getGroup(@Path ("userIdx") userIdx : Int)
    : Call<GroupResponse>
}