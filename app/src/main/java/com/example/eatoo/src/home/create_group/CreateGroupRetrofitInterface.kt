package com.example.eatoo.src.home.create_group

import com.example.eatoo.src.home.create_group.api_util.TmapKey
import com.example.eatoo.src.home.create_group.api_util.TmapUrl
import com.example.eatoo.src.home.create_group.model.CreateGroupRequest
import com.example.eatoo.src.home.create_group.model.CreateGroupResponse
import com.example.eatoo.src.home.create_group.model.address.AddressInfoResponse
import retrofit2.Call
import retrofit2.http.*

interface CreateGroupRetrofitInterface {
    @GET(TmapUrl.GET_TMAP_REVERSE_GEO_CODE)
    fun getReverseGeoCode(
        @Header("appKey") appKey: String = TmapKey.TMAP_API_KEY,
        @Query("version") version: Int = 1,
        @Query("callback") callback: String? = null,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("coordType") coordType: String? = null,
        @Query("addressType") addressType: String? = null
    ): Call<AddressInfoResponse>


    @POST("/app/groups/{userIdx}")
    fun postGroup(
        @Body createGroupRequest: CreateGroupRequest,
        @Path("userIdx") userIdx: Int
    ) : Call<CreateGroupResponse>

}