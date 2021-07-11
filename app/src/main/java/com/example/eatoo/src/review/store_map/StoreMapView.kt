package com.example.eatoo.src.review.store_map

import com.example.eatoo.src.review.store_map.model.StoreResponse
import com.example.eatoo.src.home.create_group.model.address.AddressInfoResponse
import com.example.eatoo.src.review.store_map.model.KakaoAddressResponse

interface StoreMapView {
    fun onGetStoreSuccess(response : StoreResponse)
    fun onGetStoreFail(message : String?, code : Int)

    fun onGetAddressSuccess(response : KakaoAddressResponse?)
    fun onGetAddressFail(message : String?)
}