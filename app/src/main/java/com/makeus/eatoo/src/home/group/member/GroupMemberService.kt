package com.makeus.eatoo.src.home.group.member

import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.member.model.GroupMemberResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupMemberService (val view : GroupMemberView) {

    fun tryGetGroupMember(userIdx : Int, groupIdx : Int) {
        val groupMemberRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupMemberRetrofitInterface::class.java)
        groupMemberRetrofitInterface.getGroupMembers(userIdx = userIdx, groupIdx = groupIdx).enqueue(object :
            Callback<GroupMemberResponse> {
            override fun onResponse(
                call: Call<GroupMemberResponse>,
                response: Response<GroupMemberResponse>
            ) {
                response.body()?.let {
                    if(it.isSuccess) view.onGetGroupMemberSuccess(response.body() as GroupMemberResponse)
                    else {
                        when(it.code) {
                            2001 -> view.onGetGroupMemberFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.input_jwt_request))
                            2002 -> view.onGetGroupMemberFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_jwt))
                            2005 -> view.onGetGroupMemberFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_account))
                            2006-> view.onGetGroupMemberFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_group))
                            4000 -> view.onGetGroupMemberFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.failed_db_connection))
                            else -> {}
                        }
                    }
                }

            }

            override fun onFailure(call: Call<GroupMemberResponse>, t: Throwable) {
                view.onGetGroupMemberFail(ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }

}