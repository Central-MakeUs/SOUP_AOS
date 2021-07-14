package com.example.eatoo.src.home.model

import com.google.gson.annotations.SerializedName

data class MateResultResponse(
    @SerializedName("groupIdx") val groupIdx: Int,
    @SerializedName("groupName") val groupName: String,
    @SerializedName("mateIdx") val mateIdx: Int,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("mateName") val mateName: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("membersNumber") val membersNumber: Int
)