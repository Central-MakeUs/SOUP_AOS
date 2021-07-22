package com.example.eatoo.src.home.group.vote

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupVoteBinding
import com.example.eatoo.src.home.group.vote.create_vote.CreateVoteActivity

class GroupVoteFragment
    : BaseFragment<FragmentGroupVoteBinding>(FragmentGroupVoteBinding::bind, R.layout.fragment_group_vote),
View.OnClickListener{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.cardviewNewVote.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.cardview_new_vote -> {
               startActivity(Intent(activity, CreateVoteActivity::class.java))
           }
       }
    }
}