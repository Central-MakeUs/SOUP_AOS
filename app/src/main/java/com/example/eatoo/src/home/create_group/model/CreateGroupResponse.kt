package com.example.eatoo.src.home.create_group.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("result") val result: CreateGroupResultResponse
): BaseResponse()
