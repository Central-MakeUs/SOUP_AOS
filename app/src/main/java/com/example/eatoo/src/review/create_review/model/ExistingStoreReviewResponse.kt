package com.example.eatoo.src.review.create_review.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ExistingStoreReviewResponse(
    @SerializedName("result") val result : ExistingStoreReviewResult
): BaseResponse()
