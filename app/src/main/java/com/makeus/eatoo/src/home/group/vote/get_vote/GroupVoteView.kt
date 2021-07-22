package com.makeus.eatoo.src.home.group.vote.get_vote

import com.makeus.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse
import com.makeus.eatoo.src.home.group.vote.get_vote.model.NewItemAddedResponse
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VoteDetailResponse
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VotedResponse

interface GroupVoteView {
    fun onGetGroupVoteSuccess(response : GroupVoteResponse)
    fun onGetGroupVoteFail(message : String?)

    fun onGetVoteDetailSuccess(response: VoteDetailResponse)
    fun onGetVoteDetailFail(message: String?)

    fun onPostVotedSuccess(response: VotedResponse)
    fun onPostVotedFail(message: String?)

    fun onPostNewItemSuccess(response : NewItemAddedResponse)
    fun onPostNewItemFail(message: String?)
}