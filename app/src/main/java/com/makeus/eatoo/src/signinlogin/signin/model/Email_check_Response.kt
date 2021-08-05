package com.makeus.eatoo.src.signinlogin.signin.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class Check_Response(
    @SerializedName("result")
    val result : String
):BaseResponse()

