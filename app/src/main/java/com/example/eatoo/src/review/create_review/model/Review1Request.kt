package com.example.eatoo.src.review.create_review.model

import com.example.eatoo.src.home.create_group.model.Keyword

data class Review1Request(
    val storeIdx : Int,
    val storeCategoryIdx : Int,
    val menuName : String,
    val contents : String,
    val imgUrl : String,
    val rating : Double,
    val postReviewKeywordReq : List<Keyword>,
    val link : String?
)
