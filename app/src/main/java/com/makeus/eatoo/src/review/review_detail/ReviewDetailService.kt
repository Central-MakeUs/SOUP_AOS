package com.makeus.eatoo.src.review.review_detail

import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.review.review_detail.model.PatchReviewRequest
import com.makeus.eatoo.src.review.review_detail.model.ReviewDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDetailService(val view : ReviewDetailView) {

    fun tryGetReviewDetail(userIdx : Int, reviewIdx : Int ) {
        val reviewDetailRetrofitInterface
        = ApplicationClass.sRetrofit.create(ReviewDetailRetrofitInterface::class.java)
        reviewDetailRetrofitInterface.getReviewDetail(userIdx, reviewIdx).enqueue(
            object : Callback<ReviewDetailResponse> {
            override fun onResponse(call: Call<ReviewDetailResponse>, response: Response<ReviewDetailResponse>) {
                response.body()?.let {
                    if(it.isSuccess)view.onGetReviewDetailSuccess(response.body() as ReviewDetailResponse)
                    else view.onGetReviewDetailFail(it.message)
                }
            }

            override fun onFailure(call: Call<ReviewDetailResponse>, t: Throwable) {
                view.onGetReviewDetailFail(t.message ?: ApplicationClass.applicationResources.getString(R.string.failed_connection))
            }
        })
    }

    fun tryPatchReview(userIdx : Int, reviewIdx : Int , patchReviewRequest: PatchReviewRequest) {
        val reviewDetailRetrofitInterface
                = ApplicationClass.sRetrofit.create(ReviewDetailRetrofitInterface::class.java)
        reviewDetailRetrofitInterface.patchReview(userIdx, reviewIdx, patchReviewRequest).enqueue(
            object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    response.body()?.let {
                        if(it.isSuccess)view.onPatchReviewSuccess(response.body() as BaseResponse)
                        else view.onPatchReviewFail(it.message)
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    view.onPatchReviewFail(t.message ?: ApplicationClass.applicationResources.getString(R.string.failed_connection))
                }
            })
    }
}