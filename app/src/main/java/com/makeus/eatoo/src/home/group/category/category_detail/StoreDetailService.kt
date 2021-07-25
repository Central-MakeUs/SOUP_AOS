package com.makeus.eatoo.src.home.group.category.category_detail

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.category.category_detail.model.StoreDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreDetailService(val view : StoreDetailView) {

    fun tryGetStoreDetail(userIdx: Int, storeIdx : Int) {
        val storeDetailRetrofitInterface = ApplicationClass.sRetrofit.create(
            StoreDetailRetrofitInterface::class.java
        )
        storeDetailRetrofitInterface.getStoreDetail(userIdx, storeIdx)
            .enqueue(object :
                Callback<StoreDetailResponse> {
                override fun onResponse(
                    call: Call<StoreDetailResponse>,
                    response: Response<StoreDetailResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetStoreDetailSuccess(response.body() as StoreDetailResponse)
                        else view.onGetStoreDetailFail(it.message)
                    }
                }

                override fun onFailure(call: Call<StoreDetailResponse>, t: Throwable) {
                    view.onGetStoreDetailFail(t.message)
                }

            })
    }

}