package com.makeus.eatoo.src.mypage.invite.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class InviteResponse(
    @SerializedName("result") val result : ArrayList<InviteGroupResult>
): BaseResponse()

data class InviteGroupResult(
    @SerializedName("groupIdx") val groupIdx: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Int,
    @SerializedName("membersNumber") val membersNumber: Int,
    @SerializedName("getGroupKeywordRes") val getGroupKeywordRes: ArrayList<InviteGroupKeyword>,
    @SerializedName("getGroupMembersRes") val getGroupMembersRes: ArrayList<InviteMembers>
)

data class InviteGroupKeyword(
    @SerializedName("groupKeywordIdx") val groupKeywordIdx: Int,
    @SerializedName("name") val name: String

)

data class InviteMembers(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("characters") val characters: Int,
    @SerializedName("singleStatus") val singleStatus: String

    )