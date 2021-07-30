package com.makeus.eatoo.src.wishlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentWishListBinding
import com.makeus.eatoo.like.LikeService
import com.makeus.eatoo.like.LikeView
import com.makeus.eatoo.src.home.group.category.category_detail.CategoryStoreDetailActivity
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialog
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialogInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.src.wishlist.adapter.WishListRVAdapter
import com.makeus.eatoo.src.wishlist.model.WishListResponse
import com.makeus.eatoo.util.getUserIdx


class WishListFragment :
    BaseFragment<FragmentWishListBinding>(FragmentWishListBinding::bind, R.layout.fragment_wish_list),
WishListView, WishListRVAdapter.OnStoreClickListener, RadioGroup.OnCheckedChangeListener,
    StoreToMateSuggestDialogInterface, LikeView {

    private var categoryIdx = 1
    private lateinit var wishListAdapter : WishListRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()
        binding.rdGroup.setOnCheckedChangeListener(this)
        context?.let {
            wishListAdapter = WishListRVAdapter(it, this)
            binding.rvWishlist.apply {
                adapter = wishListAdapter
                layoutManager = LinearLayoutManager(it)
            }
        }
        getWishList(binding.spinnerWishlist.selectedItemPosition)

    }

    private fun setSpinner() {
        val spinnerItem = resources.getStringArray(R.array.spinner_category)

        context?.let {
            val arrayAdapter = ArrayAdapter(
                it,
                R.layout.mate_status_spinner_item,
                spinnerItem
            )
            binding.spinnerWishlist.adapter = arrayAdapter
        }

        binding.spinnerWishlist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(!mLoadingDialog.isShowing) getWishList(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun getWishList(order: Int) {
        context?.let {
            showLoadingDialog(it)
            WishListService(this).tryGetWishList(getUserIdx(), categoryIdx, order)
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1) {
            R.id.rd_btn_ko -> categoryIdx = 1
            R.id.rd_btn_ch -> categoryIdx = 2
            R.id.rd_btn_jp -> categoryIdx = 3
            R.id.rd_btn_western -> categoryIdx = 4
            R.id.rd_btn_street -> categoryIdx = 5
            R.id.rd_btn_asian -> categoryIdx = 6
            R.id.rd_btn_desert -> categoryIdx = 7
            R.id.rd_btn_etc -> categoryIdx = 8
        }
        if(!mLoadingDialog.isShowing) getWishList(binding.spinnerWishlist.selectedItemPosition)
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

    override fun onStoreLongClicked(storeName: String) {
        val dialog = StoreToMateSuggestDialog(requireContext(), this, storeName)
        dialog.show()
    }

    override fun onGotoMateSuggestClicked(storeName: String) {
        context?.let {
            val intent = Intent(it, MateSuggestionActivity::class.java)
            intent.putExtra("storeName", storeName)
            startActivity(intent)
        }
    }

    override fun onLikeClicked(storeIdx: Int, isLiked: Boolean) {
        if(isLiked) LikeService(this).tryPostLike(getUserIdx(),storeIdx)
        else LikeService(this).tryPatchLike(getUserIdx(),storeIdx)
    }


    ////////server result

    override fun onGetWishListSuccess(response: WishListResponse) {
        dismissLoadingDialog()
        binding.llNoWishlistResult.isVisible = false
        wishListAdapter.removeAllData()
        wishListAdapter.addAllData(response.result)
        Log.d("wishlist", response.toString())
    }

    override fun onGetWishListFail(code: Int, message: String?) {
        dismissLoadingDialog()
        if(code == 2502){
            wishListAdapter.removeAllData()
            binding.llNoWishlistResult.isVisible = true
        }else {
            binding.llNoWishlistResult.isVisible = false
            showCustomToast(message?:resources.getString(R.string.failed_connection))
        }
    }



    override fun onPostLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchLikeSuccess() {
        if(!mLoadingDialog.isShowing) getWishList(binding.spinnerWishlist.selectedItemPosition)
    }

    override fun onPatchLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }




}