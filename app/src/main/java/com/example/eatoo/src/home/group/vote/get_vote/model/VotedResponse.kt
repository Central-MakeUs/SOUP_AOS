package com.example.eatoo.src.home.group.vote.get_vote.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class VotedResponse(
    @SerializedName("result") val result : UserVotedIdx
): BaseResponse()
