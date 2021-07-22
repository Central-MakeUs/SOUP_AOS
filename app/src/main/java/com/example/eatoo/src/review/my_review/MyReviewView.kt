package com.example.eatoo.src.review.my_review

import com.example.eatoo.src.review.my_review.model.MyReviewResponse

interface MyReviewView {
    fun onGetMyReviewSuccess(response : MyReviewResponse)
    fun onGetMyReviewFail(message : String?)
}