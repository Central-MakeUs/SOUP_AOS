package com.makeus.eatoo.src.suggestion.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SuggestionMateResponse(
    @SerializedName("result") val result: ArrayList<SuggestionMateResultResponse>
) : BaseResponse()