package com.example.eatoo.src.home.group.vote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupVoteBinding
import com.example.eatoo.src.home.group.vote.create_vote.CreateVoteActivity
import com.example.eatoo.src.home.group.vote.get_vote.GroupVoteService
import com.example.eatoo.src.home.group.vote.get_vote.GroupVoteView
import com.example.eatoo.src.home.group.vote.get_vote.adapter.GroupVoteRVAdapter
import com.example.eatoo.src.home.group.vote.get_vote.dialog.VoteDialog
import com.example.eatoo.src.home.group.vote.get_vote.dialog.VoteDialogInterface
import com.example.eatoo.src.home.group.vote.get_vote.model.*
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getGroupName
import com.example.eatoo.util.getUserIdx

class GroupVoteFragment
    : BaseFragment<FragmentGroupVoteBinding>(FragmentGroupVoteBinding::bind, R.layout.fragment_group_vote),
View.OnClickListener, GroupVoteView, GroupVoteRVAdapter.OnVoteClickListener, VoteDialogInterface{

    private lateinit var groupVoteAdapter : GroupVoteRVAdapter
    private lateinit var voteDialog : VoteDialog
    private var isNewItemAdded = false

    override fun onResume() {
        super.onResume()

        getGroupVote()
    }

    private fun getGroupVote() {
        context?.let {
            showLoadingDialog(it)
            GroupVoteService(this).tryGetGroupVote(getUserIdx(),getGroupIdx())
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setOnClickListeners()
        setGroupName()

    }

    private fun setGroupName() {
        binding.tvGroupName.text = getGroupName()
    }

    private fun setOnClickListeners() {
        binding.fabGroupVote.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.fab_group_vote -> {
               startActivity(Intent(activity, CreateVoteActivity::class.java))
           }
       }
    }

    override fun onGetGroupVoteSuccess(response: GroupVoteResponse) {
        dismissLoadingDialog()
        if(response.result.isNotEmpty()){

            binding.ivNoVote.visibility = View.GONE
            binding.tvNoVote.visibility = View.GONE

            context?.let {
                groupVoteAdapter = GroupVoteRVAdapter(it, response.result, this)
                binding.rvGroupVote.apply {
                    adapter = groupVoteAdapter
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                }
            }
        }else {
            binding.ivNoVote.isVisible = true
            binding.tvNoVote.isVisible = true
        }

    }

    override fun onGetGroupVoteFail(message: String?) {
        dismissLoadingDialog()
        binding.ivNoVote.isVisible = true
        binding.tvNoVote.isVisible = true
    }

    override fun onGetVoteDetailSuccess(response: VoteDetailResponse) {
        dismissLoadingDialog()

        if(isNewItemAdded) {
            voteDialog.voteDetail = response.result
            isNewItemAdded = false
        }else {
            voteDialog = VoteDialog(this)
            voteDialog.voteDetail = response.result
            voteDialog.show()
        }



    }

    override fun onGetVoteDetailFail(message: String?) {
        dismissLoadingDialog()
    }

    override fun onPostVotedSuccess(response: VotedResponse) {
        dismissLoadingDialog()
//        showCustomToast("투표하기 성공!")
        isNewItemAdded = false
    }

    override fun onPostVotedFail(message: String?) {
        dismissLoadingDialog()
    }

    override fun onPostNewItemSuccess(response : NewItemAddedResponse) {
        dismissLoadingDialog()
//        showCustomToast("항목 추가 성공!")
        isNewItemAdded = true
        GroupVoteService(this).tryGetVoteDetail(getUserIdx(), getGroupIdx(), response.result.voteIdx)


    }

    override fun onPostNewItemFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
        isNewItemAdded = false
    }

    override fun onVoteClicked(item: GroupVoteResult) {
        //투표 조회 api
        context?.let {
            showLoadingDialog(it)
            GroupVoteService(this).tryGetVoteDetail(getUserIdx(), getGroupIdx(), item.voteIdx)
        }

    }

    override fun onVoteFinishClick(voteDetail : VoteDetailResult, votedItemList : ArrayList<Int>) {
        val votedMenuList = ArrayList<VotedMenu>()
        votedItemList.forEach {
            votedMenuList.add(VotedMenu(voteDetail.getMenuRes[it].voteMenuIdx))
        }
        context?.let {
            showLoadingDialog(it)
            GroupVoteService(this).tryPostVote(getUserIdx(), getGroupIdx(), voteDetail.voteIdx, VotedRequest(votedMenuList.toList()))
        }
    }

    override fun onVoteItemAdded(voteIdx : Int, newItem: String) {
        //항목추가 api
        context?.let {
            showLoadingDialog(it)
            GroupVoteService(this).tryPostNewVoteItem(getUserIdx(), voteIdx, NewItem(newItem))
        }
    }
}