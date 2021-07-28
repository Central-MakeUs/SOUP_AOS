package com.makeus.eatoo.src.wishlist

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.wishlist.model.WishListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishListService(val view : WishListView) {

    fun tryGetWishList(userIdx: Int, storeCategoryIdx : Int, order : Int) {
        val wishListRetrofitInterface = ApplicationClass.sRetrofit.create(
            WishListRetrofitInterface::class.java
        )
        wishListRetrofitInterface.getWishList(userIdx, storeCategoryIdx, order)
            .enqueue(object :
                Callback<WishListResponse> {
                override fun onResponse(
                    call: Call<WishListResponse>,
                    response: Response<WishListResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetWishListSuccess(response.body() as WishListResponse)
                        else view.onGetWishListFail(it.code, it.message)
                    }
                }

                override fun onFailure(call: Call<WishListResponse>, t: Throwable) {
                    view.onGetWishListFail(0, t.message)
                }

            })
    }
}