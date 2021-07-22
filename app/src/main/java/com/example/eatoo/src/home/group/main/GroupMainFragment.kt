package com.example.eatoo.src.home.group.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass.Companion.GROUP_IDX
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupMainBinding
import com.example.eatoo.src.home.adapter.Home_Group_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.example.eatoo.src.home.group.main.adapter.Group_Home_Main_Mate_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.main.adapter.Group_Home_Main_Store_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.main.model.GroupMainResponse
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getUserIdx

class GroupMainFragment : BaseFragment<FragmentGroupMainBinding>(FragmentGroupMainBinding::bind, R.layout.fragment_group_main) ,GroupMainView {


    val GroupIdx = getGroupIdx()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GroupMainService(this).tryGetGroupMain(getUserIdx(),GroupIdx)

    }

    override fun onGetGroupMainSuccess(response: GroupMainResponse) {
//        showCustomToast("요청 완료")

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
        }

    }

    override fun onGetGroupMainFail(message: String?) {
    }
}