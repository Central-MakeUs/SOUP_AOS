package com.makeus.eatoo.src.home


import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.home.model.GroupResponse
import com.makeus.eatoo.src.home.model.MainCharResponse
import com.makeus.eatoo.src.home.model.MateResponse
import com.makeus.eatoo.src.home.model.NotiCountResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface GroupRetrofitInterface {

    @GET("/app/homes/{userIdx}")
    fun getMainChar(
        @Path ("userIdx") userIdx : Int
    ): Call<MainCharResponse>

    @GET("/app/homes/{userIdx}/groups")
    fun getGroup(
        @Path ("userIdx") userIdx : Int
    )
    : Call<GroupResponse>


    @GET("/app/homes/{userIdx}/mates")
    fun getMate(
        @Path ("userIdx") userIdx : Int
    ): Call<MateResponse>

    @PATCH("/app/groups/{userIdx}/delete/{groupIdx}")
    fun deleteGroup(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int
    ): Call<BaseResponse>

    @GET("/app/users/{userIdx}/notice-count")
    fun getNotiCount(
        @Path ("userIdx") userIdx : Int
    ): Call<NotiCountResponse>


//    @GET("/app/mates/{mateIdx}/end")
//    fun getMateConfirm(
//        @Path ("mateIdx") mateIdx : Int
//    ): Call<MateConfirmationResponse>



}