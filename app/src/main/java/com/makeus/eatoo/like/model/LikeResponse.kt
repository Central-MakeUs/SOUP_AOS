package com.makeus.eatoo.like.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class LikeResponse(
    @SerializedName("result") val result : LikeResult
): BaseResponse()

data class LikeResult(
    @SerializedName("likedIdx") val likedIdx : Int
)
