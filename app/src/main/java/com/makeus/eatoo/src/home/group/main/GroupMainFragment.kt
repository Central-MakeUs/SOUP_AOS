package com.makeus.eatoo.src.home.group.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGroupMainBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.FindMateActivity
import com.makeus.eatoo.src.home.group.main.adapter.Group_Home_Main_Mate_Kind_RecyclerviewAdapter
import com.makeus.eatoo.src.home.group.main.adapter.Group_Home_Main_Store_Kind_RecyclerviewAdapter
import com.makeus.eatoo.src.home.group.main.model.GroupMainResponse
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getGroupName
import com.makeus.eatoo.util.getUserIdx

class GroupMainFragment() : BaseFragment<FragmentGroupMainBinding>(FragmentGroupMainBinding::bind, R.layout.fragment_group_main) ,GroupMainView {


    val GroupIdx = getGroupIdx()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardviewReviewSuggest.setOnClickListener {
            startActivity(Intent(activity, CreateReview1Activity::class.java))
        }

        GroupMainService(this).tryGetGroupMain(getUserIdx(),GroupIdx)

        binding.findGroupMateBtn.setOnClickListener {
            startActivity(Intent(activity,FindMateActivity::class.java))
        }



    }

    @SuppressLint("SetTextI18n")
    override fun onGetGroupMainSuccess(response: GroupMainResponse) {

        if(response.result.getMateRes.size == 0){
            binding.findingMateRecyclerview.visibility = View.GONE
            binding.findingMateNoScroll.visibility = View.VISIBLE
            binding.matePlusBtn.setOnClickListener {
                startActivity(Intent(activity,Group_Mate_Suggetsion_Activity::class.java))
            }
        }
        else{
            binding.findingMateRecyclerview.visibility = View.VISIBLE
            binding.findingMateNoScroll.visibility = View.GONE
            val GroupAdapter = Group_Home_Main_Mate_Kind_RecyclerviewAdapter(response.result.getMateRes)
            binding.findingMateRecyclerview.adapter = GroupAdapter
            binding.findingMateRecyclerview.layoutManager = LinearLayoutManager(activity).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            GroupAdapter.setItemClickListener(object :
                Group_Home_Main_Mate_Kind_RecyclerviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int, mateIdx : Int) {
                    val dialog = context?.let { MateAttendDialog(it, mateIdx) }
                    dialog?.show()
                }
            })

        }


        if(response.result.getStoreRes.size > 0){
            binding.groupStoreRecyclerview.visibility = View.VISIBLE
            binding.noneGroupStoreLayout.visibility = View.GONE
            val StoreAdapter = Group_Home_Main_Store_Kind_RecyclerviewAdapter(response.result.getStoreRes)
            binding.groupStoreRecyclerview.adapter = StoreAdapter
            binding.groupStoreRecyclerview.layoutManager = LinearLayoutManager(activity).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
        }
        else{
            binding.groupStoreRecyclerview.visibility = View.GONE
            binding.noneGroupStoreLayout.visibility = View.VISIBLE
            binding.groupNameTv.text = "'" + getGroupName().toString() + "'"
        }

    }

    override fun onGetGroupMainFail(message: String?) {
    }
}