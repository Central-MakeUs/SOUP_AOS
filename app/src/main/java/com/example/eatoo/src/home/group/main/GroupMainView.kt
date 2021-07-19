package com.example.eatoo.src.home.group.main

import com.example.eatoo.src.home.group.main.model.GroupMainResponse

interface GroupMainView {
    fun onGetGroupMainSuccess(response : GroupMainResponse)

    fun onGetGroupMainFail(message : String?)
}