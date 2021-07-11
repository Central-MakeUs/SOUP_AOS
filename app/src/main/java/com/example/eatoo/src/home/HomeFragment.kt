package com.example.eatoo.src.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.USER_IDX
import com.example.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentHomeBinding
import com.example.eatoo.src.home.adapter.Home_Group_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.util.getUserIdx


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),GroupView{

    val userIdx = ApplicationClass.sSharedPreferences.getInt(USER_IDX, -1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("유전인덱스",""+getUserIdx())
        Log.d("토큰",X_ACCESS_TOKEN)

        GroupService(this).tryGetData(getUserIdx())


//        binding.groupPlusBtn.setOnClickListener {
//            startActivity(Intent(activity, CreateGroupActivity::class.java))
//        }

        binding.matePlusBtn.setOnClickListener {

        }
    }

    override fun onGetGroupSuccess(response: GroupResponse) {
        response.message?.let { showCustomToast(it) }

        val GroupSize = response.result.getGroupsRes.size

        val GroupAdapter = Home_Group_Kind_RecyclerviewAdapter(response.result.getGroupsRes,GroupSize, "BASIC")
        binding.groupRecyclerview.adapter = GroupAdapter
        binding.groupRecyclerview.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    override fun onGetGroupFail(message: String) {
        showCustomToast(message)
    }
}