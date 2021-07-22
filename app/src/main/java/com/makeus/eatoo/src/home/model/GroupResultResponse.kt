package com.makeus.eatoo.src.home.model

import com.google.gson.annotations.SerializedName

data class GroupResultResponse(
    @SerializedName("groupIdx") val groupIdx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int,
    @SerializedName("membersNumber") val membersNumber: Int,
    @SerializedName("getGroupKeywordRes") val getGroupKeywordRes: ArrayList<GroupKeyword>,
    @SerializedName("getGroupMembersRes") val getGroupMembersRes: ArrayList<GroupMembers>
)