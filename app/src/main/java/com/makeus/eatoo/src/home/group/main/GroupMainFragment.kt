package com.makeus.eatoo.src.home.group.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGroupMainBinding
import com.makeus.eatoo.like.LikeService
import com.makeus.eatoo.like.LikeView
import com.makeus.eatoo.src.home.group.category.category_detail.CategoryStoreDetailActivity
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialog
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialogInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.FindMateActivity
import com.makeus.eatoo.src.home.group.main.adapter.Group_Home_Main_Mate_Kind_RecyclerviewAdapter
import com.makeus.eatoo.src.home.group.main.adapter.Group_Home_Main_Store_Kind_RecyclerviewAdapter
import com.makeus.eatoo.src.home.group.main.model.GroupMainResponse
import com.makeus.eatoo.src.home.group.main.store_rec.StoreRecListActivity
import com.makeus.eatoo.src.review.create_review.create_review1.CreateReview1Activity
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getGroupName
import com.makeus.eatoo.util.getUserIdx

class GroupMainFragment
    : BaseFragment<FragmentGroupMainBinding>(FragmentGroupMainBinding::bind, R.layout.fragment_group_main)
    ,GroupMainView, Group_Home_Main_Store_Kind_RecyclerviewAdapter.OnStoreClickListener,
    StoreToMateSuggestDialogInterface, LikeView {


    override fun onResume() {
        super.onResume()
        context?.let {
            showLoadingDialog(it)
            GroupMainService(this).tryGetGroupMain(getUserIdx(), getGroupIdx())
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.cardviewReviewSuggest.setOnClickListener {
            startActivity(Intent(activity, CreateReview1Activity::class.java))
        }

        binding.findGroupMateBtn.setOnClickListener {
            startActivity(Intent(activity,FindMateActivity::class.java))
        }

        binding.tvMoreStoreRec.setOnClickListener {
            startActivity(Intent(activity,StoreRecListActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onGetGroupMainSuccess(response: GroupMainResponse) {
        dismissLoadingDialog()

        if(response.result.getMateRes.size == 0){
            binding.findingMateRecyclerview.visibility = View.GONE
            binding.findingMateNoScroll.visibility = View.VISIBLE
            binding.matePlusBtn.setOnClickListener {
                startActivity(Intent(activity,MateSuggestionActivity::class.java))
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
            val StoreAdapter = Group_Home_Main_Store_Kind_RecyclerviewAdapter(requireContext(), this, response.result.getStoreRes)
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
        dismissLoadingDialog()
    }

    override fun onStoreClicked(storeIdx: Int) {
        context?.let {
            val intent = Intent(it, CategoryStoreDetailActivity::class.java)
            intent.apply {
                putExtra("storeIdx", storeIdx)
            }
            startActivity(intent)
        }
    }

    override fun onStoreLongClicked(storeName: String, storeImg : String) {
        context?.let {
            val dialog = StoreToMateSuggestDialog(it, this, storeName, storeImg)
            dialog.show()
        }

    }

    override fun onLikeClicked(storeIdx: Int, isLiked: Boolean) {
        if(isLiked) LikeService(this).tryPostLike(getUserIdx(),storeIdx)
        else LikeService(this).tryPatchLike(getUserIdx(),storeIdx)
    }

    override fun onGotoMateSuggestClicked(storeName: String, storeImg : String) {
        context?.let {
            val intent = Intent(it, MateSuggestionActivity::class.java)
            intent.apply {
                putExtra("storeName", storeName)
                putExtra("storeImg", storeImg)
            }
            startActivity(intent)
        }

    }

    override fun onPostLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchLikeSuccess() {
    }

    override fun onPatchLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}