package com.example.eatoo.src.home.group.vote.get_vote.model

import com.google.gson.annotations.SerializedName

data class VoteDetailItem(
    @SerializedName("isSelected") val isSelected: String,
    @SerializedName("name") val name: String,
    @SerializedName("voteMenuIdx") val voteMenuIdx: Int,
    @SerializedName("votedNumber") val votedNumber: Int
)