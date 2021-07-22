package com.example.eatoo.src.home

import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.MateResponse


interface GroupView {
    fun onGetGroupSuccess(response : GroupResponse)

    fun onGetGroupFail(message : String)

    fun onGetMateSuccess(response : MateResponse)

    fun onGetMateFail(message : String)
}