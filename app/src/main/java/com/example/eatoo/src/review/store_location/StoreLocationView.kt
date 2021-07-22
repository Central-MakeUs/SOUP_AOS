package com.example.eatoo.src.review.store_location

import com.example.eatoo.src.review.store_location.model.KakaoSearchResponse

interface StoreLocationView {
    fun onSearchStoreSuccess(response: KakaoSearchResponse)
    fun onSearchStoreFail(message : String)
}