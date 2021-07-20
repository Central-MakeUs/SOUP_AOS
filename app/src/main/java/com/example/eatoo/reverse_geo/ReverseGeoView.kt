package com.example.eatoo.reverse_geo

import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse

interface ReverseGeoView {
    fun onGetAddressSuccess(response : KakaoAddressResponse?)
    fun onGetAddressFail(message : String?)
}