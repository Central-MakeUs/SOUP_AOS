package com.makeus.eatoo.src.home.group.category.category_map.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CategoryMapResponse(
    @SerializedName("result") val result: CategoryMapResult
) : BaseResponse()

data class CategoryMapResult(
    @SerializedName("getStoresRes") val getStoresRes: List<CategoryMapStoreInfo>,
    @SerializedName("singleStatus") val singleStatus: String
)

data class CategoryMapStoreInfo(
    @SerializedName("address") val address: String,
    @SerializedName("isLiked") val isLiked: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reviewsNumber") val reviewsNumber: Int,
    @SerializedName("storeIdx") val storeIdx: Int
)