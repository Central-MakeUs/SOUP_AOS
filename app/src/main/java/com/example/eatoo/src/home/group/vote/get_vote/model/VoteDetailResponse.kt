package com.example.eatoo.src.home.group.vote.get_vote.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class VoteDetailResponse(
   @SerializedName("result") val result: VoteDetailResult
): BaseResponse()