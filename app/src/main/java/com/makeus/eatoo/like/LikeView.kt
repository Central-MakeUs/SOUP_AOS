package com.makeus.eatoo.like

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.like.model.LikeResponse

interface LikeView {
    fun onPostLikeFail(message : String?)

    fun onPatchLikeSuccess()
    fun onPatchLikeFail(message: String?)
}