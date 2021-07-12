package com.example.eatoo.src.review.create_review

import com.example.eatoo.src.review.create_review.model.ReviewResponse

interface CreateReviewView {

    fun onPostReview2Success(response : ReviewResponse)
    fun onPostReview2Fail(message : String?)

    fun onPostReview1Success(response : ReviewResponse)
    fun onPostReview1Fail(message : String?)
}