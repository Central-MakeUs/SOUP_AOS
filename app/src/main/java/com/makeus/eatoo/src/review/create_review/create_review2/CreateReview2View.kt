package com.makeus.eatoo.src.review.create_review.create_review2

import com.makeus.eatoo.src.review.create_review.model.ReviewResponse

interface CreateReview2View {

    fun onPostReview2Success(response : ReviewResponse)
    fun onPostReview2Fail(message : String?)

    fun onPostReview1Success(response : ReviewResponse)
    fun onPostReview1Fail(message : String?)
}