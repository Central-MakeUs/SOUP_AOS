package com.example.eatoo.src.review.store_map

import com.example.eatoo.src.review.store_map.domain.GetStoreRequest
import com.example.eatoo.src.review.store_map.domain.StoreResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreMapRetrofitInterface {
    @GET ("/app/reviews/{userIdx}/stores")
    fun getStores(
        @Path ("userIdx") userIdx : Int,
        @Query ("longitude") longitude: Double,
        @Query ("latitude") latitude: Double
    ) : Call<StoreResponse>
}