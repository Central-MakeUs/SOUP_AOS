package com.example.eatoo.src.home.group.groupmatesuggestion

import com.example.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse


interface Mate_Suggestion_ActivityView {

    fun onPostMateCreateSuccess(response: CreateMateResponse)

    fun onPostMateCreateFailure(message: String)
}