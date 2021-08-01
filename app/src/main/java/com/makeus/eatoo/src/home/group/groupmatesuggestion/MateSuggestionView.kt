package com.makeus.eatoo.src.home.group.groupmatesuggestion

import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import com.makeus.eatoo.src.home.model.GroupResponse


interface MateSuggestionView {

    fun onGetGroupSuccess(response : GroupResponse)
    fun onGetGroupFail(message : String)

    fun onPostMateCreateSuccess(response: CreateMateResponse)
    fun onPostMateCreateFailure(message: String)

}