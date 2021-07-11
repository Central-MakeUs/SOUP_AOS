package com.example.eatoo.src.home.model

import com.google.gson.annotations.SerializedName

data class GroupMembers(
    @SerializedName("characters") val characters: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("singleStatus") val singleStatus: String
)