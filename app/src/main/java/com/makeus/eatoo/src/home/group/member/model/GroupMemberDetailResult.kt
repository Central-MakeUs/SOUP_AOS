package com.makeus.eatoo.src.home.group.member.model

import com.google.gson.annotations.SerializedName

data class GroupMemberDetailResult(
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("singleStatus") val singleStatus: String,
    @SerializedName("getUserKeywordRes") val getUserKeywordRes : List<MemberDietKeyword>
)

data class MemberDietKeyword(
    @SerializedName("name") val name : String,
    @SerializedName("isPrefer") val isPrefer : String,
    @SerializedName("size") val size : Int,
    @SerializedName("isNew") val isNew : String,
    @SerializedName("x") val x : Double,
    @SerializedName("y") val y : Double,
    @SerializedName("userKeywordIdx") val userKeywordIdx : Int
)