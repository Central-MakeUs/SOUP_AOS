package com.example.eatoo.src.home.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MainCharResponse(
    @SerializedName("result") val result : MainCharResult
): BaseResponse()

data class  MainCharResult(
    @SerializedName("singleStatus") val singleStatus: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color: Int
)
