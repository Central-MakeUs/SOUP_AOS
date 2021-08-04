package com.makeus.eatoo.src.mypage.profile

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.mypage.profile.model.PatchProfileRequest
import com.makeus.eatoo.src.mypage.profile.model.PatchProfileResponse
import com.makeus.eatoo.src.mypage.profile.model.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(val view : ProfileView) {
    fun tryGetProfile(userIdx: Int) {
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(
            ProfileRetrofitInterface::class.java
        )
        profileRetrofitInterface.getProfile(userIdx)
            .enqueue(object :
                Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetProfileSuccess(response.body() as ProfileResponse)
                        else view.onGetProfileFail(it.message)
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    view.onGetProfileFail(t.message)
                }

            })
    }

    fun tryPatchProfile(userIdx: Int, req: PatchProfileRequest) {
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(
            ProfileRetrofitInterface::class.java
        )
        profileRetrofitInterface.patchProfile(userIdx, req)
            .enqueue(object :
                Callback<PatchProfileResponse> {
                override fun onResponse(
                    call: Call<PatchProfileResponse>,
                    response: Response<PatchProfileResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onPatchProfileSuccess(response.body() as PatchProfileResponse)
                        else view.onPatchProfileFail(it.code, it.message)
                    }
                }

                override fun onFailure(call: Call<PatchProfileResponse>, t: Throwable) {
                    view.onPatchProfileFail(0, t.message)
                }

            })
    }
}