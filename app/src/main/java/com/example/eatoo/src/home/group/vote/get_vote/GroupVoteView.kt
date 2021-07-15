package com.example.eatoo.src.home.group.vote.get_vote

import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse

interface GroupVoteView {
    fun onGetGroupVoteSuccess(response : GroupVoteResponse)
    fun onGetGroupVoteFail(message : String?)
}