package com.example.eatoo.src.review.store_map

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.review.store_map.model.StoreResponse
import com.example.eatoo.src.review.store_map.model.AllStoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreMapService(val view: StoreMapView) {

    fun tryGetStore(userIdx: Int, longitude: Double, latitude: Double) {
        val storeMapRetrofitInterface = ApplicationClass.sRetrofit.create(
            StoreMapRetrofitInterface::class.java
        )
        storeMapRetrofitInterface.getStores(userIdx, longitude, latitude)
            .enqueue(object :
                Callback<StoreResponse> {
                override fun onResponse(
                    call: Call<StoreResponse>,
                    response: Response<StoreResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetStoreSuccess(response.body() as StoreResponse)
                        else view.onGetStoreFail(it.message, it.code)
                    }
                }

                override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                    view.onGetStoreFail(t.message, 0)
                }

            })
    }

    fun tryGetAllStore(userIdx: Int) {
        val storeMapRetrofitInterface = ApplicationClass.sRetrofit.create(
            StoreMapRetrofitInterface::class.java
        )
        storeMapRetrofitInterface.getAllStore(userIdx)
            .enqueue(object :
                Callback<AllStoreResponse> {
                override fun onResponse(
                    call: Call<AllStoreResponse>,
                    response: Response<AllStoreResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetAllStoreSuccess(response.body() as AllStoreResponse)
                        else view.onGetAllStoreFail(it.message)
                    }
                }

                override fun onFailure(call: Call<AllStoreResponse>, t: Throwable) {
                    view.onGetAllStoreFail(t.message)
                }

            })
    }

}