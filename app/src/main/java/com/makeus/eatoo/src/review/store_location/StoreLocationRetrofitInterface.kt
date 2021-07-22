package com.makeus.eatoo.src.review.store_location

import com.makeus.eatoo.src.review.store_location.model.KakaoSearchResponse
import com.makeus.eatoo.src.review.store_map.kakao_api.KakaoKey
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