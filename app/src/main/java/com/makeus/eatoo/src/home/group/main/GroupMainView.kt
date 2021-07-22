package com.makeus.eatoo.src.home.group.main

import com.makeus.eatoo.src.home.group.main.model.GroupMainResponse

interface GroupMainView {
    fun onGetGroupMainSuccess(response : GroupMainResponse)

    fun onGetGroupMainFail(message : String?)
}