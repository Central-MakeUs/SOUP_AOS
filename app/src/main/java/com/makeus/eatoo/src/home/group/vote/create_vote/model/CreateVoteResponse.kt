package com.makeus.eatoo.src.home.group.vote.create_vote.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CreateVoteResponse(
    @SerializedName("result") val result : CreateVoteRequest
) : BaseResponse()
