package com.makeus.eatoo.src.review.review_detail.model

data class PatchReviewRequest(
    val contents: String,
    val link: String,
    val menuName: String,
    val patchReviewKeywordReq: List<PatchReviewKeywordReq>,
    val rating: Double
)

data class PatchReviewKeywordReq(
    val name: String
)