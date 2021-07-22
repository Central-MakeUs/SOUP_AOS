package com.example.eatoo.src.home.model

import com.google.gson.annotations.SerializedName

data class GroupKeyword(
    @SerializedName("groupKeywordIdx") val groupKeywordIdx: Int,
    @SerializedName("name") val name: String
)