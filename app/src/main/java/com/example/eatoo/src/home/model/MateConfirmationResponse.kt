package com.example.eatoo.src.home.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MateConfirmationResponse(
    @SerializedName("result") val result: MateConfirmationResultResponse
) : BaseResponse()

data class MateConfirmationResultResponse(
    @SerializedName("name") val name: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("time") val time: String,
    @SerializedName("headCount") val headCount: Int,
    @SerializedName("getMemberRes") val getMemberRes: ArrayList<MateConfirmationMemberRes>
)

data class MateConfirmationMemberRes(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color : Int,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("singleStatus") val singleStatus: String

)

