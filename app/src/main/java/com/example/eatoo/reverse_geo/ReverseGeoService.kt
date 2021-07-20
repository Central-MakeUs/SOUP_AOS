package com.example.eatoo.reverse_geo

import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.review.store_map.kakao_api.KakaoRetrofit
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReverseGeoService(val view : ReverseGeoView) {

    fun tryGetAddress(lat : Double, lng: Double) {

        KakaoRetrofit.kakaoAddressService.getAddress(longitude = lng, latitude = lat).enqueue(object :
            Callback<KakaoAddressResponse> {
            override fun onResponse(
                call: Call<KakaoAddressResponse>,
                response: Response<KakaoAddressResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.onGetAddressSuccess(response.body() as KakaoAddressResponse)
                    }
                } else {
                    view.onGetAddressFail(response.message())
                }
            }
            override fun onFailure(call: Call<KakaoAddressResponse>, t: Throwable) {
                view.onGetAddressFail(ApplicationClass.applicationResources.getString(R.string.search_address_fail))
            }

        })
    }
}