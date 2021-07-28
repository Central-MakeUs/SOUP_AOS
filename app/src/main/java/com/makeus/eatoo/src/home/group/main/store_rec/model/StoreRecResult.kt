package com.makeus.eatoo.src.home.group.main.store_rec.model

import com.google.gson.annotations.SerializedName

data class StoreRecResult(
    @SerializedName("address") val address: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("isLiked") val isLiked: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("storeIdx") val storeIdx: Int,
    @SerializedName("storeName") val storeName: String
)