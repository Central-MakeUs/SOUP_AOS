package com.makeus.eatoo.src.home.group.category.category_list

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.group.category.category_list.model.StoreCategoryListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListService (val view : CategoryListView){

    fun tryGetStoreCategoryList(userIdx: Int, storeIdx : Int, storeCategoryIdx : Int, order : Int) {
        val categoryListRetrofitInterface = ApplicationClass.sRetrofit.create(
            CategoryListRetrofitInterface::class.java
        )
        categoryListRetrofitInterface.getStoreCategoryList(userIdx, storeIdx, storeCategoryIdx, order)
            .enqueue(object :
                Callback<StoreCategoryListResponse> {
                override fun onResponse(
                    call: Call<StoreCategoryListResponse>,
                    response: Response<StoreCategoryListResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetCategoryListSuccess(response.body() as StoreCategoryListResponse)
                        else view.onGetCategoryListFail(it.code, it.message)
                    }
                }

                override fun onFailure(call: Call<StoreCategoryListResponse>, t: Throwable) {
                    view.onGetCategoryListFail(0, t.message)
                }

            })
    }
}