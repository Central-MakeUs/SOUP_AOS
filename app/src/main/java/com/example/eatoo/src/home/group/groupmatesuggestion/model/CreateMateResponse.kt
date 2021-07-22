package com.example.eatoo.src.home.group.groupmatesuggestion.model

import com.example.eatoo.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class CreateMateResponse(
        @SerializedName("result")
        val result : Result_Mate_Create
):BaseResponse()

data class Result_Mate_Create(
        @SerializedName("mateIdx")
        val mateIdx : Int
)