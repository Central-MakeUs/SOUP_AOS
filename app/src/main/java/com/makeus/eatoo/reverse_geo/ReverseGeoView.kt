package com.makeus.eatoo.reverse_geo

import com.makeus.eatoo.src.review.store_map.model.KakaoAddressResponse

interface ReverseGeoView {
    fun onGetAddressSuccess(response : KakaoAddressResponse?)
    fun onGetAddressFail(message : String?)
}