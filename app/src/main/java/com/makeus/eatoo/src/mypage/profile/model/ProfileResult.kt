package com.makeus.eatoo.src.mypage.profile.model

import com.google.gson.annotations.SerializedName

data class ProfileResult(
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("nickName") val nickName: String
)