package com.makeus.eatoo.src.review.review_detail.model

import com.makeus.eatoo.src.home.create_group.model.Keyword

data class PatchReviewRequest(
    val contents: String,
    val link: String,
    val menuName: String,
    val patchReviewKeywordReq: List<Keyword>,
    val rating: Double
)