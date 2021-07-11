package com.example.eatoo.src.review.store_map

import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.create_group.CreateGroupRetrofitInterface
import com.example.eatoo.src.home.create_group.model.CreateGroupResponse
import com.example.eatoo.src.review.store_map.domain.GetStoreRequest
import com.example.eatoo.src.review.store_map.domain.StoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreMapService(val view: StoreMapActivity) {

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
}