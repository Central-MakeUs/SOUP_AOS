package com.example.eatoo.src.review.store_location

import com.example.eatoo.src.review.store_location.model.KakaoSearchResponse
import com.example.eatoo.src.review.store_map.kakao_api.KakaoKey
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreLocationRetrofitInterface {

    @GET("/v2/local/search/keyword.{format}")
    fun getStoreSearch(
        @Header("Authorization") auth : String = "KakaoAK ${KakaoKey.KAKAO_API_KEY}",
        @Path("format") format : String = "json",
        @Query("query") query: String?,
        @Query("page") page: Int
    ) : Call<KakaoSearchResponse>
}