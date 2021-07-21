package com.example.eatoo.src.home

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.MateConfirmationResponse
import com.example.eatoo.src.home.model.MateResponse
import com.example.eatoo.src.home.model.MateResultResponse
import com.example.eatoo.src.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupService(val view: GroupView) {

    fun tryGetGroupData(userIdx : Int ) {
        val groupinerface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupinerface.getGroup(userIdx).enqueue(object : Callback<GroupResponse> {
            override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {
                view.onGetGroupSuccess(response.body() as GroupResponse)
            }

            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                view.onGetGroupFail(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetMateData(userIdx : Int ) {
        val groupinerface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupinerface.getMate(userIdx).enqueue(object : Callback<MateResponse> {
            override fun onResponse(call: Call<MateResponse>, response: Response<MateResponse>) {
                view.onGetMateSuccess(response.body() as MateResponse)
            }

            override fun onFailure(call: Call<MateResponse>, t: Throwable) {
                view.onGetMateFail(t.message ?: "통신 오류")
            }
        })
    }
// // 메이트 확정 부분입니다. 아직 완벽하게 되어 있지 않아서 일단 이렇게 둘게요!
//    fun tryGetCofirmData(mateIdx : Int ) {
//        val mateinerface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
//        mateinerface.getMateConfirm(mateIdx).enqueue(object : Callback<MateConfirmationResponse> {
//            override fun onResponse(call: Call<MateConfirmationResponse>, response: Response<MateConfirmationResponse>) {
//                view.onGetMateConfirmSuccess(response.body() as MateConfirmationResponse)
//            }
//
//            override fun onFailure(call: Call<MateConfirmationResponse>, t: Throwable) {
//                view.onGetMateConfirmFail(t.message ?: "통신 오류")
//            }
//        })
//    }
}