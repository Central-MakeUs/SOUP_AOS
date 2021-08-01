package com.makeus.eatoo.src.home

import com.makeus.eatoo.src.home.model.GroupResponse
import com.makeus.eatoo.src.home.model.MainCharResponse
import com.makeus.eatoo.src.home.model.MateResponse
import com.makeus.eatoo.src.home.model.NotiCountResponse


interface GroupView {
    fun onGetGroupSuccess(response : GroupResponse)
    fun onGetGroupFail(message : String)

    fun onGetMateSuccess(response : MateResponse)
    fun onGetMateFail(message : String)

    fun onGetMainCharSuccess(response : MainCharResponse)
    fun onGetMainCharFail(message: String?)

    fun onDeleteGroupSuccess()
    fun onDeleteGroupFail(message: String?)

    fun onGetNotiCountSuccess(response : NotiCountResponse)
    fun onGetNotiCountFail(message: String?)

}