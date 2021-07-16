package com.example.eatoo.src.home.group.vote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupVoteBinding
import com.example.eatoo.src.home.group.vote.create_vote.CreateVoteActivity
import com.example.eatoo.src.home.group.vote.get_vote.GroupVoteService
import com.example.eatoo.src.home.group.vote.get_vote.GroupVoteView
import com.example.eatoo.src.home.group.vote.get_vote.adapter.GroupVoteRVAdapter
import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResponse
import com.example.eatoo.src.home.group.vote.get_vote.model.GroupVoteResult
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getGroupName
import com.example.eatoo.util.getUserIdx

class GroupVoteFragment
    : BaseFragment<FragmentGroupVoteBinding>(FragmentGroupVoteBinding::bind, R.layout.fragment_group_vote),
View.OnClickListener, GroupVoteView, GroupVoteRVAdapter.OnVoteClickListener{

    private lateinit var groupVoteAdapter : GroupVoteRVAdapter

    override fun onResume() {
        super.onResume()

        getGroupVote()
    }

    private fun getGroupVote() {
        context?.let {
            showLoadingDialog(it)
            GroupVoteService(this).tryGetGroupVote(getUserIdx(),28) // getGroupIdx()
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
        Log.d("groupVoteFrag", response.toString())
        context?.let {
            groupVoteAdapter = GroupVoteRVAdapter(it, response.result, this)
            binding.rvGroupVote.apply {
                adapter = groupVoteAdapter
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            }
        }
    }

    override fun onGetGroupVoteFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onVoteClicked(item: GroupVoteResult) {
        showCustomToast(item.name)
    }
}