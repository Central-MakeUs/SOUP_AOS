package com.example.eatoo.src.mypage.finding_invite

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.mypage.finding_invite.model.FindGroupRequest
import com.example.eatoo.src.mypage.finding_invite.model.FindGroupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindInviteDialogService (val view : FindInviteDialogView){


    fun tryPostParticipate(postRequest: FindGroupRequest ,userIdx :Int){
        val logininerface = ApplicationClass.sRetrofit.create(FindInviteDialogInterface::class.java)
        logininerface.postGroupParticipate(postRequest,userIdx).enqueue(object :
            Callback<FindGroupResponse> {
            override fun onResponse(call: Call<FindGroupResponse>, response: Response<FindGroupResponse>) {
                view.onPostGroupParticipateSuccess(response.body() as FindGroupResponse)
            }

            override fun onFailure(call: Call<FindGroupResponse>, t: Throwable) {
                view.onPostGroupParticipateFailure(t.message ?: "통신 오류")
            }
        })
    }
}