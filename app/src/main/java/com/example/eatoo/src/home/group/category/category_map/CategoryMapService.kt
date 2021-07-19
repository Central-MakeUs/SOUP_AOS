package com.example.eatoo.src.home.group.category.category_map

import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.home.group.category.category_map.model.CategoryMapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMapService (val view : CategoryMapView){

    fun tryGetCategoryMapStore(userIdx: Int) {
        val categoryMapRetrofitInterface = ApplicationClass.sRetrofit.create(
            CategoryMapRetrofitInterface::class.java
        )
        categoryMapRetrofitInterface.getCategoryMapStore(userIdx)
            .enqueue(object :
                Callback<CategoryMapResponse> {
                override fun onResponse(
                    call: Call<CategoryMapResponse>,
                    response: Response<CategoryMapResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetCategoryMapStoreSuccess(response.body() as CategoryMapResponse)
                        else view.onGetCategoryMapStoreFail(it.message)
                    }
                }

                override fun onFailure(call: Call<CategoryMapResponse>, t: Throwable) {
                    view.onGetCategoryMapStoreFail(t.message)
                }

            })
    }


}