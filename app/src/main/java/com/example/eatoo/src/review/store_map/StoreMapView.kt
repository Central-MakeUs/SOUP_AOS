package com.example.eatoo.src.review.store_map

import com.example.eatoo.src.review.store_map.domain.StoreResponse

interface StoreMapView {
    fun onGetStoreSuccess(response : StoreResponse)
    fun onGetStoreFail(message : String?, code : Int)
}