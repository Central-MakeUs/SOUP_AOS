package com.example.eatoo.src.home

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.eatoo.src.home.adapter.Home_Mate_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.MateResponse
import com.example.eatoo.util.getUserIdx
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.home.group.GroupActivity
import com.example.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.example.eatoo.util.getUserNickName


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),GroupView{

    val userIdx = ApplicationClass.sSharedPreferences.getInt(USER_IDX, -1)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("유전인덱스",""+getUserIdx())
        Log.d("토큰",X_ACCESS_TOKEN)

        GroupService(this).tryGetGroupData(getUserIdx())
        GroupService(this).tryGetMateData(getUserIdx())


//        binding.groupPlusBtn.setOnClickListener {
//            startActivity(Intent(activity, CreateGroupActivity::class.java))
//        }

        binding.matePlusBtn.setOnClickListener {
            startActivity(Intent(activity, Group_Mate_Suggetsion_Activity::class.java))
        }

        binding.mateOverviewBtn.setOnClickListener {
        }

        binding.cardviewReviewSuggest.setOnClickListener {
            startActivity(Intent(activity, CreateReview1Activity::class.java))
        }

        binding.usetNameHomeTv.text = getUserNickName() + binding.usetNameHomeTv.text
    }

    override fun onGetGroupSuccess(response: GroupResponse) {
        response.message?.let { showCustomToast(it) }

        if(response.code == 1000) {
            binding.matePlusBtn.isClickable = true
            binding.noneGroupLayoutMain.visibility = View.GONE
            binding.groupRecyclerview.visibility = View.VISIBLE
            val GroupSize = response.result.getGroupsRes.size

            val GroupAdapter = Home_Group_Kind_RecyclerviewAdapter(
                response.result.getGroupsRes,
                GroupSize,
                "BASIC"
            )
            binding.groupRecyclerview.adapter = GroupAdapter
            binding.groupRecyclerview.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

            GroupAdapter.setItemClickListener(object :
                Home_Group_Kind_RecyclerviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int, groupIdx : Int, state : String) {
                    if (position == GroupSize && state == "plus") {
                        startActivity(Intent(activity, CreateGroupActivity::class.java))
                    }
                    else if(position != GroupSize  && state == "Group_activity"){
                        ApplicationClass.sSharedPreferences.edit()
                            .putInt(ApplicationClass.GROUP_IDX,  groupIdx).apply()
                        startActivity(Intent(activity, GroupActivity::class.java))
                    }
                }
            })
        }
        else if(response.code == 2500){
            binding.noneGroupLayoutMain.visibility = View.VISIBLE
            binding.groupRecyclerview.visibility = View.GONE

            binding.matePlusBtn.isClickable = false

            binding.groupPlusBtn1.setOnClickListener{
                startActivity(Intent(activity,CreateGroupActivity::class.java))
            }
            binding.groupPlusBtn2.setOnClickListener{
                startActivity(Intent(activity,CreateGroupActivity::class.java))
            }

        }
    }

    override fun onGetGroupFail(message: String) {
        showCustomToast(message)
    }
//Mate 조회
    override fun onGetMateSuccess(response: MateResponse) {
       response.message?.let { showCustomToast(it) }

        if(response.code == 2501){
            binding.mateNonePlusLayout.visibility = View.VISIBLE
            binding.homeMateRecylerview.visibility = View.GONE
        }
        else{
            binding.mateNonePlusLayout.visibility = View.GONE
            binding.homeMateRecylerview.visibility = View.VISIBLE
            val MateList = response.result

            val MateAdapter = Home_Mate_Kind_RecyclerviewAdapter(MateList!!)
            binding.homeMateRecylerview.adapter = MateAdapter
            binding.homeMateRecylerview.layoutManager = LinearLayoutManager(activity).also {
                it.orientation = LinearLayoutManager.VERTICAL
                @Override
                fun canScrollVertically() : Boolean {
                    return false
                }

                @Override
                fun canScrollHorizontally() : Boolean  {
                    return false
                }
            }
            binding.homeMateRecylerview.canScrollVertically(0)

            //binding.homeMateRecylerview.layoutManager = LinearLayoutManager(activity).also{ canScrollVertically()}
        }


    }

    override fun onGetMateFail(message: String) {
    }

}
