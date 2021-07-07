package com.example.eatoo.src.home.model

import com.google.gson.annotations.SerializedName

data class GroupResultResponse(
    @SerializedName("getGroupKeywordRes") val getGroupKeywordRes: List<GroupKeyword>?,
    @SerializedName("getGroupMembersRes") val getGroupMembersRes: List<GroupMembers>?,
    @SerializedName("groupIdx") val groupIdx: Int,
    @SerializedName("membersNumber") val membersNumber: Int,
    @SerializedName("name") val name: String
)