package com.makeus.eatoo.src.review.create_review.create_review1

import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.review.create_review.CreateReviewRetrofitInterface
import com.makeus.eatoo.src.review.create_review.model.ExistingStoreReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReview1Service(val view : CreateReview1View) {

    fun tryGetStoreInfo(userIdx: Int, storeIdx : Int) {
        val createReviewRetrofitInterface = ApplicationClass.sRetrofit.create(
            CreateReviewRetrofitInterface::class.java
        )
        createReviewRetrofitInterface.getStoreInfo(userIdx, storeIdx)
            .enqueue(object :
                Callback<ExistingStoreReviewResponse> {
                override fun onResponse(
                    call: Call<ExistingStoreReviewResponse>,
                    response: Response<ExistingStoreReviewResponse>
                ) {
                    response.body()?.let {
                        if (it.isSuccess) view.onGetStoreInfoSuccess(response.body() as ExistingStoreReviewResponse)
                        else view.onGetStoreInfoFail(it.message)
                    }
                }

                override fun onFailure(call: Call<ExistingStoreReviewResponse>, t: Throwable) {
                    view.onGetStoreInfoFail(t.message)
                }

            })
    }


}