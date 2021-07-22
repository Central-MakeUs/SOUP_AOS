package com.makeus.eatoo.src.home.group.groupmatesuggestion.model

import com.makeus.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CreateMateResponse(
        @SerializedName("result")
        val result : Result_Mate_Create
):BaseResponse()

data class Result_Mate_Create(
        @SerializedName("mateIdx")
        val mateIdx : Int
)