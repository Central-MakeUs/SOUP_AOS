package com.example.eatoo.src.home.group.member.model

import com.google.gson.annotations.SerializedName

data class GroupMember(
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("userIdx") val userIdx: Int
)