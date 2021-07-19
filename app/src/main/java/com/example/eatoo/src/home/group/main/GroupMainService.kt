package com.example.eatoo.src.home.group.main

import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.single_status.SingleResultResponse
import com.example.eatoo.single_status.SingleRetrofitInterface
import com.example.eatoo.src.home.group.main.model.GroupMainResponse
import com.example.eatoo.src.home.group.member.model.GroupMemberResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupMainService (val view : GroupMainView) {

    fun tryGetGroupMain(userIdx : Int, groupIdx : Int) {
        val groupMainRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupMainRetrofitInterface::class.java)
        groupMainRetrofitInterface.getGroupMembers(userIdx = userIdx, groupIdx = groupIdx).enqueue(object :
            Callback<GroupMainResponse> {
            override fun onResponse(
                call: Call<GroupMainResponse>,
                response: Response<GroupMainResponse>
            ) {
                response.body()?.let {
                    if(it.isSuccess) view.onGetGroupMainSuccess(response.body() as GroupMainResponse)
                    else {
                        when(it.code) {
                            2001 -> view.onGetGroupMainFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.input_jwt_request))
                            2002 -> view.onGetGroupMainFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_jwt))
                            2005 -> view.onGetGroupMainFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_account))
                            2006-> view.onGetGroupMainFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_group))
                            4000 -> view.onGetGroupMainFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.failed_db_connection))
                            else -> {}
                        }
                    }
                }

            }

            override fun onFailure(call: Call<GroupMainResponse>, t: Throwable) {
                view.onGetGroupMainFail(ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }

}