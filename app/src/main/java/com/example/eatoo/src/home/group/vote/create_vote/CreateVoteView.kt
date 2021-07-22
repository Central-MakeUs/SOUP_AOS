package com.example.eatoo.src.home.group.vote.create_vote

import com.example.eatoo.src.home.group.vote.create_vote.model.CreateVoteResponse

interface CreateVoteView {
    fun onPostCreateVoteSuccess()
    fun onPostCreateVoteFail(message : String?)
}