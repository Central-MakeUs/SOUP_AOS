package com.example.eatoo.src.home.create_group

import android.util.Log
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.create_group.api_util.TmapRetrofit
import com.example.eatoo.src.home.create_group.model.CreateGroupRequest
import com.example.eatoo.src.home.create_group.model.CreateGroupResponse
import com.example.googlemapsapiprac.response.address.AddressInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateGroupService(val view :CreateGroupActivity) {

    fun tryGetCurrentAddress(lat : Double, lon: Double) {

        TmapRetrofit.addressService.getReverseGeoCode(lat = lat, lon = lon).enqueue(object :
            Callback<AddressInfoResponse> {
            override fun onResponse(
                call: Call<AddressInfoResponse>,
                response: Response<AddressInfoResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.onGetCurrentAddressSuccess(response.body() as AddressInfoResponse)
                    }
                } else {
                    view.onGetCurrentAddressFail(ApplicationClass.applicationResources.getString(R.string.search_address_fail))
                }
            }
            override fun onFailure(call: Call<AddressInfoResponse>, t: Throwable) {
                view.onGetCurrentAddressFail(ApplicationClass.applicationResources.getString(R.string.search_address_fail))
            }

        })
    }

    fun tryPostGroup(userIdx : Int, createGroup : CreateGroupRequest) {
        val createGroupRetrofitInterface = ApplicationClass.sRetrofit.create(
            CreateGroupRetrofitInterface::class.java)
        createGroupRetrofitInterface.postGroup(userIdx = userIdx, createGroupRequest = createGroup).enqueue(object : Callback<CreateGroupResponse> {
            override fun onResponse(
                call: Call<CreateGroupResponse>,
                response: Response<CreateGroupResponse>
            ) {
                response.body()?.let {
                        if(it.isSuccess) view.onPostGroupSuccess(response.body() as CreateGroupResponse)
                        else {
                            when(it.code) {
                                2001 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.input_jwt_request))
                                2002 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.invalid_jwt))
                                2005 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.invalid_account))
                                2050 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.input_group_name_request))
                                2051 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.input_color_request))
                                2052 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.input_latitude))
                                2053 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.input_longitude))
                                2054 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.check_group_name))
                                2055 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.check_color_range))
                                2056 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.check_latitude))
                                2057 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.check_longitude))
                                2102 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.check_keyword_count))
                                4000 -> view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
                            }
                        }
                    }

            }

            override fun onFailure(call: Call<CreateGroupResponse>, t: Throwable) {
                view.onPostGroupFail(ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }
}