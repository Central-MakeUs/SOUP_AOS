package com.example.eatoo.single_status

import android.util.Log
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.config.BaseResponse
import com.example.eatoo.src.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleService (val view : SingleView){

    fun tryPatchSingleStatus(userIdx : Int) {
        val singleRetrofitInterface = ApplicationClass.sRetrofit.create(
            SingleRetrofitInterface::class.java)
        singleRetrofitInterface.patchSingleStatus(userIdx = userIdx).enqueue(object :
            Callback<SingleResultResponse> {
            override fun onResponse(
                call: Call<SingleResultResponse>,
                response: Response<SingleResultResponse>
            ) {
                response.body()?.let {
                    if(it.isSuccess) view.onPatchSingleStatusSuccess()
                    else {
                        when(it.code) {
                            2001 -> view.onPatchSingleStatusFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.input_jwt_request))
                            2002 -> view.onPatchSingleStatusFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_jwt))
                            2005 -> view.onPatchSingleStatusFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.invalid_account))
                            4000 -> view.onPatchSingleStatusFail(
                                ApplicationClass.applicationResources.getString(
                                    R.string.failed_db_connection))
                            else -> {}
                        }
                    }
                }

            }

            override fun onFailure(call: Call<SingleResultResponse>, t: Throwable) {
                view.onPatchSingleStatusFail(ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }
}