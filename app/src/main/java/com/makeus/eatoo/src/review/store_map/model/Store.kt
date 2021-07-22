package com.makeus.eatoo.src.review.store_map.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("getStoreKeywordRes") val getStoreKeywordRes: List<GetStoreKeywordRes>,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("storeIdx") val storeIdx: Int,
    @SerializedName("address") val address: String
)

data class GetStoreKeywordRes(
    val name: String
)