package com.makeus.eatoo.src.home.group.main.store_rec

import com.makeus.eatoo.src.home.group.main.store_rec.model.StoreRecResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreRecRetrofitInterface {

    @GET("/app/stores/{userIdx}/{groupIdx}/{storeCategoryIdx}")
    fun getStoreRecList(
        @Path("userIdx") userIdx : Int,
        @Path("groupIdx") groupIdx : Int,
        @Path("storeCategoryIdx") storeCategoryIdx : Int,
        @Query("order") order : Int
    ) : Call<StoreRecResponse>
}