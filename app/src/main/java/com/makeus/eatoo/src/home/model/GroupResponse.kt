package com.makeus.eatoo.src.home.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("result") val result: ArrayList<GroupResultResponse>
) : BaseResponse()