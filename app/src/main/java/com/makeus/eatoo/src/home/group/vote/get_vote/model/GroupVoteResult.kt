package com.makeus.eatoo.src.home.group.vote.get_vote.model

import com.google.gson.annotations.SerializedName


data class GroupVoteResult(
    @SerializedName("getVoteKeywordRes") val getVoteKeywordRes: List<VoteKeyword>,
    @SerializedName("name") val name: String,
    @SerializedName("voteIdx") val voteIdx: Int,
    @SerializedName("votedNumber") val votedNumber: Int
)