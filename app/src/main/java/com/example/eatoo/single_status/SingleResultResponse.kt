package com.example.eatoo.single_status

import com.example.eatoo.config.BaseResponse
import com.example.eatoo.src.home.create_group.model.CreateGroupResultResponse
import com.google.gson.annotations.SerializedName

data class SingleResultResponse(
    @SerializedName("result") val result: String
) : BaseResponse()
