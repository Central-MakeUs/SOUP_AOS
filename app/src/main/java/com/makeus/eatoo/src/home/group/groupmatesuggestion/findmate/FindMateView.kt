package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate

import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model.GroupMateResponse


interface FindMateView {
    fun onGetFindMateSuccess(response : GroupMateResponse)

    fun onGetFindMateFail(message : String?)
}