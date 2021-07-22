package com.makeus.eatoo.src.mypage.profile.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class PatchProfileResponse(
    @SerializedName("result") val result : String
): BaseResponse()
