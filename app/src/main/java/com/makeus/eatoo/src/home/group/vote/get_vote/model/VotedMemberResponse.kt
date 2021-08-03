package com.makeus.eatoo.src.home.group.vote.get_vote.model

import com.google.gson.annotations.SerializedName
import com.makeus.eatoo.config.BaseResponse

data class VotedMemberResponse(
    @SerializedName("result") val result: VotedMemberResult
) : BaseResponse()

data class VotedMemberResult(
    @SerializedName("menuName") val menuName: String,
    @SerializedName("getMembersRes") val getMembersRes: List<VotedMember>
)

data class VotedMember(
    @SerializedName("userIdx") val userIdx : Int,
    @SerializedName("characters") val characters : Int,
    @SerializedName("color") val color : Int,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("singleStatus") val singleStatus : String
)
