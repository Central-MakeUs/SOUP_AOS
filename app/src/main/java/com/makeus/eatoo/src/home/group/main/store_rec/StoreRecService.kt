package com.makeus.eatoo.src.home.group.main.store_rec

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.main.store_rec.model.StoreRecResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreRecService(val view : StoreRecView) {

    fun tryGetStoreRecList(userIdx: Int, storeIdx : Int, storeCategoryIdx : Int, order : Int) {
        val storeRecRetrofitInterface = ApplicationClass.sRetrofit.create(
            StoreRecRetrofitInterface::class.java
        )
        storeRecRetrofitInterface.getStoreRecList(userIdx, storeIdx, storeCategoryIdx, order)
            .enqueue(object :
                Callback<StoreRecResponse> {
                override fun onResponse(
                    call: Call<StoreRecResponse>,
                    response: Response<StoreRecResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetStoreRecSuccess(response.body() as StoreRecResponse)
                        else view.onGetStoreRecFail(it.code, it.message)
                    }
                }

                override fun onFailure(call: Call<StoreRecResponse>, t: Throwable) {
                    view.onGetStoreRecFail(0, t.message)
                }

            })
    }
}