package com.makeus.eatoo.src.home.group.vote.get_vote.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class VoteDetailResponse(
   @SerializedName("result") val result: VoteDetailResult
): BaseResponse()