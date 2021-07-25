package com.makeus.eatoo.src.home.group.category.category_detail

import com.makeus.eatoo.src.home.group.category.category_detail.model.StoreDetailResponse

interface StoreDetailView {
    fun onGetStoreDetailSuccess(response : StoreDetailResponse)
    fun onGetStoreDetailFail(message : String?)
}