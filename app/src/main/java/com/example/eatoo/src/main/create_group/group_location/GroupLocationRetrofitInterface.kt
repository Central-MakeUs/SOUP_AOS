package com.example.eatoo.src.main.create_group.group_location

import com.example.eatoo.src.main.create_group.api_util.TmapKey
import com.example.eatoo.src.main.create_group.api_util.TmapUrl
import com.example.googlemapsapiprac.response.search.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GroupLocationRetrofitInterface {

    @GET(TmapUrl.GET_TMAP_LOCATION)
    fun getSearchLocation(
        @Header("appKey") appKey: String = TmapKey.TMAP_API_KEY,
        @Query("version") version: Int = 1,
        @Query("callback") callback: String? = null,
        @Query("count") count: Int = 20, //필요
        @Query("searchKeyword") keyword: String, //필요
        @Query("areaLLCode") areaLLCode: String? = null,
        @Query("areaLMCode") areaLMCode: String? = null,
        @Query("resCoordType") resCoordType: String? = null,
        @Query("searchType") searchType: String? = null,
        @Query("multiPoint") multiPoint: String? = null,
        @Query("searchtypCd") searchtypCd: String? = null,
        @Query("radius") radius: String? = null,
        @Query("reqCoordType") reqCoordType: String? = null,
        @Query("centerLon") centerLon: String? = null,
        @Query("centerLat") centerLat: String? = null
    ): Call<SearchResponse>


}