package com.example.eatoo.src.home

import com.example.eatoo.src.home.model.GroupResponse

interface GroupView {
    fun onGetGroupSuccess(response : GroupResponse)

    fun onGetGroupFail(message : String)
}