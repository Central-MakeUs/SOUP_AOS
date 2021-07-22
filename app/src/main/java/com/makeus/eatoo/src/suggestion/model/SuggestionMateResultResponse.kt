package com.makeus.eatoo.src.suggestion.model

import com.google.gson.annotations.SerializedName

data class SuggestionMateResultResponse(
    @SerializedName("groupIdx") val groupIdx: Int,
    @SerializedName("groupName") val groupName: String,
    @SerializedName("mateIdx") val mateIdx: Int,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("mateName") val mateName: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("membersNumber") val membersNumber: Int
)