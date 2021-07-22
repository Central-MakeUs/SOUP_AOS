package com.makeus.eatoo.src.mypage.invite

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.makeus.eatoo.src.mypage.invite.model.InviteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InviteService (val view : InviteActivityView) {

    fun tryGetInviteGroupData(userIdx : Int ) {
        val inviteinerface = ApplicationClass.sRetrofit.create(InviteInterface::class.java)
        inviteinerface.getInviteGroup(userIdx).enqueue(object : Callback<InviteResponse> {
            override fun onResponse(call: Call<InviteResponse>, response: Response<InviteResponse>) {
                view.onGetInviteGroupDateSuccess(response.body() as InviteResponse)
            }

            override fun onFailure(call: Call<InviteResponse>, t: Throwable) {
                view.onGetInviteGroupDateFail(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetInviteCodeData(userIdx : Int , groupIdx : Int ) {
        val userinerface = ApplicationClass.sRetrofit.create(InviteInterface::class.java)
        userinerface.getInviteCode(userIdx,groupIdx).enqueue(object : Callback<InviteCodeResponse> {
            override fun onResponse(call: Call<InviteCodeResponse>, response: Response<InviteCodeResponse>) {
                view.onGetInviteCodeDateSuccess(response.body() as InviteCodeResponse)
            }

            override fun onFailure(call: Call<InviteCodeResponse>, t: Throwable) {
                view.onGetInviteCodeDateFail(t.message ?: "통신 오류")
            }
        })
    }

}