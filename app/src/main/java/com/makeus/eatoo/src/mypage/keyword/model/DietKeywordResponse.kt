package com.makeus.eatoo.src.mypage.keyword.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class DietKeywordResponse(
    @SerializedName("result") val result : List<DietKeywordResult>
): BaseResponse()

data class DietKeywordResult(
    @SerializedName("name") val name : String,
    @SerializedName("isPrefer") val isPrefer : String,
    @SerializedName("size") val size : Int,
    @SerializedName("isNew") val isNew : String,
    @SerializedName("x") val x : Double,
    @SerializedName("y") val y : Double,
    @SerializedName("userKeywordIdx") val userKeywordIdx : Int
    )
