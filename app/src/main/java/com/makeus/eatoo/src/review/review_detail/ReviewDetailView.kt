package com.makeus.eatoo.src.review.review_detail

import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.src.review.review_detail.model.ReviewDetailResponse

interface ReviewDetailView {
    fun onGetReviewDetailSuccess(response : ReviewDetailResponse)
    fun onGetReviewDetailFail(message : String?)

    fun onPatchReviewSuccess(response: BaseResponse)
    fun onPatchReviewFail(message: String?)
}