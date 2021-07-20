package com.example.eatoo.reverse_geo

import com.example.eatoo.src.review.store_map.kakao_api.KakaoKey
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReverseGeoRetrofitInterface {
    @GET("/v2/local/geo/coord2address.{format}")
    fun getAddress(
        @Header("Authorization") auth : String = "KakaoAK ${KakaoKey.KAKAO_API_KEY}",
        @Path("format") format : String = "json",
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ) : Call<KakaoAddressResponse>
}