package com.example.eatoo.src.review.create_review.model

import com.google.gson.annotations.SerializedName

data class ExistingStoreReviewResult(
    @SerializedName("storeName") val storeName : String,
    @SerializedName("storeCategoryIdx") val storeCategoryIdx : Int
)
