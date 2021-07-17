package com.example.eatoo.src.home.group.vote.get_vote.dialog

import com.example.eatoo.src.home.group.vote.get_vote.model.VoteDetailResult

interface VoteDialogInterface {
    fun onVoteFinishClick(voteDetail : VoteDetailResult, votedItemList : ArrayList<Int>)
    fun onReVoteFinishClicked(voteDetail : VoteDetailResult, votedItemList : ArrayList<Int>)
    fun onVoteItemAdded(voteIdx : Int, newItem : String)
}