package com.example.eatoo.config

import com.google.gson.annotations.SerializedName

open class BaseResponse (
    @SerializedName("isSuccess") var isSuccess: Boolean = false,
    @SerializedName("code") var code: Int = 0,
    @SerializedName("message") var message: String? = null
)