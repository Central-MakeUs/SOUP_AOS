package com.example.eatoo.src.home.create_group

import android.util.Log
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.create_group.api_util.TmapRetrofit
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
                    Log.d("tmap", response.toString())
                    response.body()?.let {
                        view.onGetCurrentAddressSuccess(response.body() as AddressInfoResponse)
                    }
                } else {
                     Log.d("tmap", response.toString())
                    view.onGetCurrentAddressFail(ApplicationClass.applicationResources.getString(R.string.search_address_fail))
                }
            }
            override fun onFailure(call: Call<AddressInfoResponse>, t: Throwable) {
                //Log.d("tmap", t.message.toString())
                view.onGetCurrentAddressFail(ApplicationClass.applicationResources.getString(R.string.search_address_fail))
            }

        })
    }
}