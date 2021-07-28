package com.makeus.eatoo.src.home.group.main.store_rec

import com.makeus.eatoo.src.home.group.main.store_rec.model.StoreRecResponse

interface StoreRecView {
    fun onGetStoreRecSuccess(response : StoreRecResponse)
    fun onGetStoreRecFail(code: Int, message : String?)
}