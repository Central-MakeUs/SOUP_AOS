package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class GroupMateResponse(
    @SerializedName("result") val result: ArrayList<GroupMateResult>
): BaseResponse()

data class GroupMateResult(
    @SerializedName("mateIdx") val mateIdx: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("isAttended") val isAttended: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("mateName") val mateName: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("time") val time: String,
    @SerializedName("headCount") val headCount: String,
    @SerializedName("timeLimit") val timeLimit: String,
    @SerializedName("membersNumber") val membersNumber: String
)