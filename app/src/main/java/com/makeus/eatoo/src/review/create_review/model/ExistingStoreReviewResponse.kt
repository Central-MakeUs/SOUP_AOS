package com.makeus.eatoo.src.review.create_review.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ExistingStoreReviewResponse(
    @SerializedName("result") val result : ExistingStoreReviewResult
): BaseResponse()
