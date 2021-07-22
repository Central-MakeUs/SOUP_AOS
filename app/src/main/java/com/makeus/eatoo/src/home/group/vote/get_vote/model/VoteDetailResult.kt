package com.makeus.eatoo.src.home.group.vote.get_vote.model

import com.google.gson.annotations.SerializedName

data class VoteDetailResult(
    @SerializedName("endDate")  val endDate: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("getMenuRes") val getMenuRes: List<VoteDetailItem>,
    @SerializedName("hasDuplicate") val hasDuplicate: String,
    @SerializedName("hasTimeLimit") val hasTimeLimit: String,
    @SerializedName("isAddible") val isAddible: String,
    @SerializedName("isAnonymous") val isAnonymous: String,
    @SerializedName("isVoted") val isVoted: String,
    @SerializedName("name") val name: String,
    @SerializedName("voteIdx") val voteIdx: Int
)