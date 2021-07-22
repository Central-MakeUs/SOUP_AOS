package com.makeus.eatoo.src.review.my_review.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MyReviewResponse(
   @SerializedName("result") val result: List<MyReviewResult>
) : BaseResponse()