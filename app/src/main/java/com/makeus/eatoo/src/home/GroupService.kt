package com.makeus.eatoo.src.home

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.home.model.GroupResponse
import com.makeus.eatoo.src.home.model.MainCharResponse
import com.makeus.eatoo.src.home.model.MateResponse
import com.makeus.eatoo.src.home.model.NotiCountResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupService(val view: GroupView) {

    fun tryGetMainChar(userIdx : Int ) {
        val groupRetrofitInterface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupRetrofitInterface.getMainChar(userIdx).enqueue(object : Callback<MainCharResponse> {
            override fun onResponse(call: Call<MainCharResponse>, response: Response<MainCharResponse>) {
                response.body()?.let {
                    if(it.isSuccess)view.onGetMainCharSuccess(response.body() as MainCharResponse)
                    else view.onGetMainCharFail(it.message)
                }

            }

            override fun onFailure(call: Call<MainCharResponse>, t: Throwable) {
                view.onGetMainCharFail(t.message ?: "통신 오류")
            }
        })
    }

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

    fun tryDeleteGroup(userIdx : Int , groupIdx : Int) {
        val groupRetrofitInterface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupRetrofitInterface.deleteGroup(userIdx, groupIdx).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                response.body()?.let {
                    if(it.isSuccess)view.onDeleteGroupSuccess()
                    else view.onDeleteGroupFail(it.message)
                }

            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteGroupFail(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetNotiCount(userIdx : Int) {
        val groupRetrofitInterface = ApplicationClass.sRetrofit.create(GroupRetrofitInterface::class.java)
        groupRetrofitInterface.getNotiCount(userIdx).enqueue(object : Callback<NotiCountResponse> {
            override fun onResponse(call: Call<NotiCountResponse>, response: Response<NotiCountResponse>) {
                response.body()?.let {
                    if(it.isSuccess)view.onGetNotiCountSuccess(response.body() as NotiCountResponse)
                    else view.onGetNotiCountFail(it.message)
                }

            }

            override fun onFailure(call: Call<NotiCountResponse>, t: Throwable) {
                view.onGetNotiCountFail(t.message ?: "통신 오류")
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