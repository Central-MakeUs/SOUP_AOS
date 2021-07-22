package com.example.eatoo.src.home.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("result") val result: GroupResultResponse
) : BaseResponse()