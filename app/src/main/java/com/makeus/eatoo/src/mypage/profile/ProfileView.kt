package com.makeus.eatoo.src.mypage.profile

import com.makeus.eatoo.src.mypage.profile.model.PatchProfileResponse
import com.makeus.eatoo.src.mypage.profile.model.ProfileResponse

interface ProfileView {
    fun onGetProfileSuccess(response : ProfileResponse)
    fun onGetProfileFail(message : String?)

    fun onPatchProfileSuccess(response: PatchProfileResponse)
    fun onPatchProfileFail(code : Int, message: String?)
}