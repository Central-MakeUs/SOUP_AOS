package com.example.eatoo.src.mypage.finding_invite.model

import com.google.gson.annotations.SerializedName

data class FindGroupRequest(
    @SerializedName("code") val code : String
)