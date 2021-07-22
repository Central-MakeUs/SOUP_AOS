package com.example.eatoo.src.home.group.member.model

import com.google.gson.annotations.SerializedName

data class GroupMemberResult(
    @SerializedName("groupIdx")  val groupIdx: Int,
    @SerializedName("members")  val members: List<GroupMember>,
    @SerializedName("name")  val name: String,
    @SerializedName("singleStatus") val singleStatus: String
)