package com.makeus.eatoo.src.review.create_review.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("result") val result : ReviewIdx
) : BaseResponse()
