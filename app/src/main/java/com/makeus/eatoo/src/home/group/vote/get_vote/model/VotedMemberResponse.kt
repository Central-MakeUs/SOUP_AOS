package com.makeus.eatoo.src.home.group.vote.get_vote.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class VotedMemberResponse(
    @SerializedName("result") val result : List<VotedMemberResult>
): BaseResponse()

data class VotedMemberResult(
    @SerializedName("userIdx") val userIdx : Int,
    @SerializedName("characters") val characters : Int,
    @SerializedName("color") val color : Int,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("singleStatus") val singleStatus : String
)
