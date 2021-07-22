package com.example.eatoo.src.home.group.vote.get_vote.model

import com.google.gson.annotations.SerializedName

data class VoteKeyword(
    @SerializedName("name") val name: String,
    @SerializedName("voteKeywordIdx") val voteKeywordIdx: Int
)