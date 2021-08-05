package com.makeus.eatoo.src.home.group.member

import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.member.model.GroupMemberDetailResponse
import com.makeus.eatoo.src.home.group.member.model.GroupMemberResponse
import com.makeus.eatoo.src.mypage.invite.InviteInterface
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse
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

    fun tryGetInviteCode(userIdx : Int , groupIdx : Int ) {

        val inviteInterface = ApplicationClass.sRetrofit.create(InviteInterface::class.java)
        inviteInterface.getInviteCode(userIdx,groupIdx).enqueue(object : Callback<InviteCodeResponse> {
            override fun onResponse(call: Call<InviteCodeResponse>, response: Response<InviteCodeResponse>) {
                response.body()?.let {
                    if(it.isSuccess) view.onGetInviteCodeDateSuccess(response.body() as InviteCodeResponse)
                    else view.onGetInviteCodeDateFail(it.message)
                }

            }

            override fun onFailure(call: Call<InviteCodeResponse>, t: Throwable) {
                view.onGetInviteCodeDateFail(t.message ?:ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }
        })
    }

    fun tryGetMemberDetail(myIdx : Int , userIdx : Int ) {

        val groupMemberRetrofitInterface = ApplicationClass.sRetrofit.create(GroupMemberRetrofitInterface::class.java)
        groupMemberRetrofitInterface.getGroupMemberDetail(myIdx, userIdx).enqueue(object : Callback<GroupMemberDetailResponse> {
            override fun onResponse(call: Call<GroupMemberDetailResponse>, response: Response<GroupMemberDetailResponse>) {
                response.body()?.let {
                    if(it.isSuccess) view.onGetMemberDetailSuccess(response.body() as GroupMemberDetailResponse)
                    else view.onGetMmeberDetailFail(it.message)
                }

            }

            override fun onFailure(call: Call<GroupMemberDetailResponse>, t: Throwable) {
                view.onGetMmeberDetailFail(t.message ?:ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }
        })
    }

}