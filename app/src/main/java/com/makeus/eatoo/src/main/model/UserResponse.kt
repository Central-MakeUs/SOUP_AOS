package com.makeus.eatoo.src.main.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("result") val result: UserResultResponse

):BaseResponse()

data class UserResultResponse(
    @SerializedName("color") val color: Int,
    @SerializedName("characters") val characters: Int,
    @SerializedName("nickName") val nickName: String
)