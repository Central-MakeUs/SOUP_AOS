package com.makeus.eatoo.src.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.ApplicationClass.Companion.USER_IDX
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentHomeBinding
import com.makeus.eatoo.single_status.SingleService
import com.makeus.eatoo.single_status.SingleView
import com.makeus.eatoo.src.home.adapter.HomeGroupKindRecyclerviewAdapter
import com.makeus.eatoo.src.home.adapter.HomeMateKindRecyclerviewAdapter
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.src.home.model.GroupResponse
import com.makeus.eatoo.src.home.model.MateResponse
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.src.home.create_group.CreateGroupActivity
import com.makeus.eatoo.src.home.group.GroupActivity
import com.makeus.eatoo.src.home.model.MainCharResponse
import com.makeus.eatoo.src.home.notification.NotificationActivity
import com.makeus.eatoo.src.main.MainActivity
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.src.suggestion.SuggestionFragment
import com.makeus.eatoo.util.EatooCharList
import com.makeus.eatoo.util.getUserNickName


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    GroupView, SingleView, HomeGroupKindRecyclerviewAdapter.OnGroupLongClick, GroupDeleteDialogInterface,
View.OnClickListener{

    private var changeToSingle = false
    val userIdx = ApplicationClass.sSharedPreferences.getInt(USER_IDX, -1)

    override fun onResume() {
        super.onResume()

        GroupService(this).tryGetMainChar(getUserIdx())
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        loadGroup()
        loadMate()

        binding.usetNameHomeTv.text = getUserNickName() + binding.usetNameHomeTv.text

    }

    private fun setOnClickListeners() {
        binding.customToolbar.setRightIconClickListener(this)
        binding.customToolbar.setLeftIconClickListener(this)
        binding.mateOverviewBtn.setOnClickListener(this)
        binding.matePlusBtn.setOnClickListener(this)
        binding.cardviewReviewSuggest.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.mate_overview_btn -> {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host, SuggestionFragment())
                    .commitAllowingStateLoss()
                val bottomNavigationView =
                    activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                bottomNavigationView?.menu?.findItem(R.id.suggestionFragment)?.isChecked = true
            }
            R.id.mate_plus_btn -> {
                startActivity(Intent(activity, MateSuggestionActivity::class.java))
            }
            R.id.cardview_review_suggest -> {
                startActivity(Intent(activity, CreateReview1Activity::class.java))
            }
            R.id.iv_toolbar_right -> {
                changeToSingle = binding.customToolbar.rightIcon.drawable.constantState?.equals(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_icon)?.constantState
                ) ?:false
                SingleService(this).tryPatchSingleStatus(getUserIdx())
            }
            R.id.iv_toolbar_left -> {
                startActivity(Intent(activity, NotificationActivity::class.java))
            }
        }
    }

    private fun loadMate() {
        context?.let {
            GroupService(this).tryGetMateData(getUserIdx())
        }
    }

    private fun loadGroup() {
        context?.let {
            showLoadingDialog(it)
            GroupService(this).tryGetGroupData(getUserIdx())
        }
    }

    override fun onGetGroupSuccess(response: GroupResponse) {
        dismissLoadingDialog()

        if(response.code == 1000) {


//            binding.matePlusBtn.isClickable = true
            binding.noneGroupLayoutMain.visibility = View.GONE
            binding.groupRecyclerview.visibility = View.VISIBLE
            val GroupSize = response.result.size

            val GroupAdapter = HomeGroupKindRecyclerviewAdapter(
                response.result,
                GroupSize,
                "BASIC",
                this
            )
            binding.groupRecyclerview.adapter = GroupAdapter
            binding.groupRecyclerview.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

            GroupAdapter.setItemClickListener(object :
                HomeGroupKindRecyclerviewAdapter.ItemClickListener {
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
    override fun onGroupLongClicked(groupIdx : Int, groupName : String) {
        //그룹 삭제 다이얼로그
        context?.let {
            val dialog =  GroupDeleteDialog(it, this, groupIdx, groupName)
            dialog.show()
        }


    }

    override fun onGroupDeleteClicked(groupIdx: Int) {
        context?.let {
            showLoadingDialog(it)
            GroupService(this).tryDeleteGroup(getUserIdx(), groupIdx)
        }

    }

    private fun setSingleStatus(singleStatus: String) {
        if(singleStatus == "ON") binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)
    }

    override fun onGetGroupFail(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)

    }

    /*

    mate 조회

     */
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

            context?.let {
                val MateAdapter = HomeMateKindRecyclerviewAdapter(it, MateList!!)
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
            }


            //binding.homeMateRecylerview.layoutManager = LinearLayoutManager(activity).also{ canScrollVertically()}
        }


    }

    override fun onGetMateFail(message: String) {
        dismissLoadingDialog()
    }

    override fun onGetMainCharSuccess(response: MainCharResponse) {
        Log.d("homeFragment", response.toString())
        setSingleStatus(response.result.singleStatus)
        setMainChar(response.result.color, response.result.characters, response.result.singleStatus)
    }

    private fun setMainChar(color: Int, characters: Int, singleStatus: String) {
        Log.d("homeFragment", "here")
        val memberColor = if(color != 0) color -1 else 0
        val memberChar = if(characters != 0) characters -1 else 0
        binding.ivMainChar.setImageResource(EatooCharList[(memberColor*5) + memberChar])
        if(singleStatus == "ON"){
            val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
            val matrix = ColorMatrixColorFilter(grayScale)
            binding.ivMainChar.colorFilter= matrix
        }else {
            binding.ivMainChar.colorFilter = null
        }

    }

    override fun onGetMainCharFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onDeleteGroupSuccess() {
        dismissLoadingDialog()
        loadGroup()
    }

    override fun onDeleteGroupFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchSingleStatusSuccess() {

        if(changeToSingle) binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icons)
        else binding.customToolbar.rightIcon.setImageResource(R.drawable.ic_icon)

        GroupService(this).tryGetMainChar(getUserIdx())
    }

    override fun onPatchSingleStatusFail(message: String?) {
        showCustomToast(message ?: resources.getString(R.string.failed_connection))
    }




}
