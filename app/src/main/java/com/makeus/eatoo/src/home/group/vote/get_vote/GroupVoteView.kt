package com.makeus.eatoo.src.home.group.vote.get_vote

import com.makeus.eatoo.src.home.group.vote.get_vote.model.*

interface GroupVoteView {
    fun onGetGroupVoteSuccess(response : GroupVoteResponse)
    fun onGetGroupVoteFail(message : String?)

    fun onGetVoteDetailSuccess(response: VoteDetailResponse)
    fun onGetVoteDetailFail(message: String?)

    fun onPostVotedSuccess(response: VotedResponse)
    fun onPostVotedFail(message: String?)

    fun onPostNewItemSuccess(response : NewItemAddedResponse)
    fun onPostNewItemFail(message: String?)

    fun onGetVotedMemberSuccess(response: VotedMemberResponse)
    fun onGetVotedMemberFail(message: String?)
}