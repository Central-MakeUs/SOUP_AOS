package com.example.eatoo.single_status

import com.example.eatoo.config.BaseResponse

interface SingleView {

    fun onPatchSingleStatusSuccess()
    fun onPatchSingleStatusFail(message : String?)
}