package com.makeus.eatoo.src.home.group.category.category_detail

import com.makeus.eatoo.src.home.group.category.category_detail.model.StoreDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreDetailRetrofitInterface {

    @GET("/app/stores/{userIdx}/{storeIdx}")
    fun getStoreDetail(
        @Path ("userIdx") userIdx : Int,
        @Path("storeIdx") storeIdx : Int
    ) : Call<StoreDetailResponse>

}