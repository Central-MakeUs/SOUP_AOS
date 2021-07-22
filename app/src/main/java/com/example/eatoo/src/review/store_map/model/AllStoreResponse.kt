package com.example.eatoo.src.review.store_map.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class AllStoreResponse(
    @SerializedName("result") val result : List<AllStoreResult>
) : BaseResponse()

data class AllStoreResult(
    @SerializedName("getStoreKeywordRes") val getStoreKeywordRes: List<GetStoreKeywordRes>,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("latitude")  val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("storeIdx") val storeIdx: Int
)
