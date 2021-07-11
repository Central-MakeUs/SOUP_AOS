package com.example.eatoo.src.home.model

import com.google.gson.annotations.SerializedName

data class GroupResultResponse(
    @SerializedName("singleStatus") val singleStatus: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("getGroupsRes") val getGroupsRes: ArrayList<GetGroupsRes>
)

data class GetGroupsRes (
    @SerializedName("groupIdx") val groupIdx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("membersNumber") val membersNumber: Int,
    @SerializedName("getGroupKeywordRes") val getGroupKeywordRes: ArrayList<GroupKeyword>,
    @SerializedName("getGroupMembersRes") val getGroupMembersRes: ArrayList<GroupMembers>

)