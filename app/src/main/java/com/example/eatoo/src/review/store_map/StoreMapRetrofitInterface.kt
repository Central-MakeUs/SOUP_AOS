package com.example.eatoo.src.review.store_map

import com.example.eatoo.src.review.store_map.kakao_api.KakaoKey
import com.example.eatoo.src.review.store_map.model.AllStoreResponse
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse
import com.example.eatoo.src.review.store_map.model.StoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreMapRetrofitInterface {
    @GET("/app/reviews/{userIdx}/all-stores")
    fun getAllStore(
        @Path ("userIdx") userIdx : Int
    ) : Call<AllStoreResponse>

    @GET ("/app/reviews/{userIdx}/stores")
    fun getStores(
        @Path ("userIdx") userIdx : Int,
        @Query ("longitude") longitude: Double,
        @Query ("latitude") latitude: Double
    ) : Call<StoreResponse>

    @GET ("/v2/local/geo/coord2address.{format}")
    fun getAddress(
        @Header("Authorization") auth : String = "KakaoAK ${KakaoKey.KAKAO_API_KEY}",
        @Path("format") format : String = "json",
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ) : Call<KakaoAddressResponse>
}