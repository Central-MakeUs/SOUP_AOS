package com.makeus.eatoo.src.review.create_review.model

import com.makeus.eatoo.src.home.create_group.model.Keyword

data class Review2Request(
    val longitude : Double,
    val latitude : Double,
    val address : String,
    val storeName : String,
    val storeCategoryIdx : Int,
    val menuName : String,
    val contents : String,
    val imgUrl : String,
    val rating : Double,
    val postReviewKeywordReq : List<Keyword>,
    val link : String?
)
