package com.makeus.eatoo.src.review.store_map

import com.makeus.eatoo.src.review.store_map.model.StoreResponse
import com.makeus.eatoo.src.review.store_map.model.AllStoreResponse

interface StoreMapView {
    fun onGetStoreSuccess(response : StoreResponse)
    fun onGetStoreFail(message : String?, code : Int)

    fun onGetAllStoreSuccess(response : AllStoreResponse)
    fun onGetAllStoreFail(message: String?)
}