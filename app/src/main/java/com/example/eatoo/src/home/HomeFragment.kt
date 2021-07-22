package com.example.eatoo.src.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.USER_IDX
import com.example.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentHomeBinding
import com.example.eatoo.single_status.SingleService
import com.example.eatoo.single_status.SingleView
import com.example.eatoo.src.home.adapter.Home_Group_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.adapter.Home_Mate_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.src.home.model.MateResponse
import com.example.eatoo.util.getUserIdx
import com.example.eatoo.src.home.create_group.CreateGroupActivity
import com.example.eatoo.src.home.group.GroupActivity
import com.example.eatoo.src.home.model.MainCharResponse
import com.example.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.example.eatoo.util.EatooCharList
import com.example.eatoo.util.getUserNickName


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    GroupView, SingleView {

    private var changeToSingle = false
    val userIdx = ApplicationClass.sSharedPreferences.getInt(USER_IDX, -1)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("유전인덱스",""+getUserIdx())
        Log.d("토큰",X_ACCESS_TOKEN)

        GroupService(this).tryGetMainChar(getUserIdx())
        GroupService(this).tryGetGroupData(getUserIdx())
        GroupService(this).tryGetMateData(getUserIdx())
        context?.let { showLoadingDialog(it) }


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

        binding.customToolbar.rightIcon.setOnClickListener {
            changeToSingle = binding.customToolbar.rightIcon.drawable.constantState?.equals(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_icon)?.constantState
            ) ?:false
            SingleService(this).tryPatchSingleStatus(getUserIdx())
        }
    }

    override fun onGetGroupSuccess(response: GroupResponse) {
        dismissLoadingDialog()

        if(response.code == 1000) {


            binding.matePlusBtn.isClickable = true
            binding.noneGroupLayoutMain.visibility = View.GONE
            binding.groupRecyclerview.visibility = View.VISIBLE
            val GroupSize = response.result.size

            val GroupAdapter = Home_Group_Kind_RecyclerviewAdapter(
                response.result,
                GroupSize,
                "BASIC"
            )
            binding.groupRecyclerview.adapter = GroupAdapter
            binding.groupRecyclerview.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

            GroupAdapter.setItemClickListener(object :
                Home_Group_Kind_RecyclerviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int, groupIdx : Int, groupname : String, state : String) {
                    if (position == GroupSize && state == "plus") {
                        startActivity(Intent(activity, CreateGroupActivity::class.java))
                    }
                    else if(position != GroupSize  && state == "Group_activity"){
                        ApplicationClass.sSharedPreferences.edit()
                            .putInt(ApplicationClass.GROUP_IDX,  groupIdx).apply()
                        ApplicationClass.sSharedPreferences.edit()
                            .putString(ApplicationClass.GROUP_NAME,  groupname).apply()
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

    private fun setSingleStatus(singleStatus: String) {
        if(singleStatus == "ON") binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)
    }

    override fun onGetGroupFail(message: String) {
        dismissLoadingDialog()
//        showCustomToast(message)

    }
//Mate 조회
    override fun onGetMateSuccess(response: MateResponse) {
        dismissLoadingDialog()

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
        dismissLoadingDialog()
    }

    override fun onGetMainCharSuccess(response: MainCharResponse) {
        setSingleStatus(response.result.singleStatus)
        setMainChar(response.result.color, response.result.characters, response.result.singleStatus)
    }

    private fun setMainChar(color: Int, characters: Int, singleStatus: String) {
        val memberColor = if(color != 0) color -1 else 0
        val memberChar = if(characters != 0) characters -1 else 0
        binding.ivMainChar.setImageResource(EatooCharList[(memberColor*5) + memberChar])
        if(singleStatus == "ON"){
            val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
            val matrix = ColorMatrixColorFilter(grayScale)
            binding.ivMainChar.colorFilter= matrix
        }

    }

    override fun onGetMainCharFail(message: String) {
        showCustomToast(message)
    }

    override fun onPatchSingleStatusSuccess() {

        if(changeToSingle) binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)
    }

    override fun onPatchSingleStatusFail(message: String?) {
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }

}
