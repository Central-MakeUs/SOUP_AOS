package com.example.eatoo.src.home.group.vote.get_vote

import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse
import com.example.eatoo.src.home.group.vote.get_vote.model.VoteDetailResponse
import com.example.eatoo.src.home.group.vote.get_vote.model.VotedResponse

interface GroupVoteView {
    fun onGetGroupVoteSuccess(response : GroupVoteResponse)
    fun onGetGroupVoteFail(message : String?)

    fun onGetVoteDetailSuccess(response: VoteDetailResponse)
    fun onGetVoteDetailFail(message: String?)

    fun onPostVotedSuccess(response: VotedResponse)
    fun onPostVotedFail(message: String?)

    fun onPostNewItemSuccess()
    fun onPostNewItemFail(message: String?)
}