package com.makeus.eatoo.src.home.group.vote.create_vote

interface CreateVoteView {
    fun onPostCreateVoteSuccess()
    fun onPostCreateVoteFail(message : String?)
}