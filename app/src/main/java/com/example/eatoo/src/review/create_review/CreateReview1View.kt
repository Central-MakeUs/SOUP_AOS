package com.example.eatoo.src.review.create_review

import com.example.eatoo.src.review.create_review.model.ExistingStoreReviewResponse

interface CreateReview1View {
    fun onGetStoreInfoSuccess(response : ExistingStoreReviewResponse)
    fun onGetStoreInfoFail(message: String?)
}