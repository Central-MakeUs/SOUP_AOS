package com.example.eatoo.src.review.my_review.model

import com.google.gson.annotations.SerializedName

data class MyReviewResult(
    @SerializedName("address") val address: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("imgUrl")  val imgUrl: String,
    @SerializedName("menuName") val menuName: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reviewIdx")  val reviewIdx: Int,
    @SerializedName("storeName") val storeName: String
)