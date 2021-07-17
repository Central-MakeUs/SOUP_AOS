package com.example.eatoo.src.home.group.vote.get_vote.model

data class VotedRequest(
    val postVoteMenuReq : ArrayList<VotedMenu>
)

data class VotedMenu(
    val voteMenuIdx : Int
)
