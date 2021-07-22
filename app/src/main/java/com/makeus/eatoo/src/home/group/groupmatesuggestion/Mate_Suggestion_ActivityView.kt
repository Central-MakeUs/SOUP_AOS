package com.makeus.eatoo.src.home.group.groupmatesuggestion

import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse


interface Mate_Suggestion_ActivityView {

    fun onPostMateCreateSuccess(response: CreateMateResponse)

    fun onPostMateCreateFailure(message: String)
}