package com.makeus.eatoo.src.home.create_group.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("result") val result: CreateGroupResultResponse
): BaseResponse()
