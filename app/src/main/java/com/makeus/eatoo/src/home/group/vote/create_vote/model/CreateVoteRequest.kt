package com.makeus.eatoo.src.home.group.vote.create_vote.model

import com.makeus.eatoo.src.home.create_group.model.Keyword

data class CreateVoteRequest(
    val hasDuplicate: String,
    val hasTimeLimit: String,
    val isAddible: String,
    val isAnonymous: String,
    val name: String,
    val postKeywordReq: List<Keyword>,
    val postMenuReq: List<Keyword>,
    val timeLimit: String
)