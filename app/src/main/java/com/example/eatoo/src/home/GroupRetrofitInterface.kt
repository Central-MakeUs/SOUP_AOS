package com.example.eatoo.src.home


import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.MateConfirmationResponse
import com.example.eatoo.src.home.model.MateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupRetrofitInterface {

    @GET("/app/homes/{userIdx}") //여기 아님. 메인홈 그룹조회는 아직 구현 안 됨.
    fun getGroup(
        @Path ("userIdx") userIdx : Int
    )
    : Call<GroupResponse>


    @GET("/app/homes/{userIdx}/mates")
    fun getMate(
        @Path ("userIdx") userIdx : Int
    ): Call<MateResponse>


//    @GET("/app/mates/{mateIdx}/end")
//    fun getMateConfirm(
//        @Path ("mateIdx") mateIdx : Int
//    ): Call<MateConfirmationResponse>



}