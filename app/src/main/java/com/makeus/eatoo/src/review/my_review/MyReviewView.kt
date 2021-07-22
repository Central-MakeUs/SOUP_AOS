package com.makeus.eatoo.src.review.my_review

import com.makeus.eatoo.src.review.my_review.model.MyReviewResponse

interface MyReviewView {
    fun onGetMyReviewSuccess(response : MyReviewResponse)
    fun onGetMyReviewFail(message : String?)
}