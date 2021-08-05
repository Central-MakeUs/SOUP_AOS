package com.makeus.eatoo.src.home.group.category.category_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGroupCategoryListBinding
import com.makeus.eatoo.like.LikeService
import com.makeus.eatoo.like.LikeView
import com.makeus.eatoo.src.home.group.category.category_detail.CategoryStoreDetailActivity
import com.makeus.eatoo.src.home.group.category.category_list.adapter.CategoryListRVAdapter
import com.makeus.eatoo.src.home.group.category.category_list.model.StoreCategoryListResponse
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialog
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialogInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getUserIdx

class GroupCategoryListFragment(
    val cfm: FragmentManager,
    val listener : CategoryListenerInterface
    ) : BaseFragment<FragmentGroupCategoryListBinding>(FragmentGroupCategoryListBinding::bind, R.layout.fragment_group_category_list),
RadioGroup.OnCheckedChangeListener, CategoryListView, CategoryListRVAdapter.OnStoreClickListener,
    StoreToMateSuggestDialogInterface, LikeView {

    private var categoryIdx = 1
    private lateinit var storeListAdapter : CategoryListRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()
        binding.rdGroup.setOnCheckedChangeListener(this)
        context?.let {
            storeListAdapter = CategoryListRVAdapter(it, this)
            binding.rvCategoryList.apply {
                adapter = storeListAdapter
                layoutManager = LinearLayoutManager(it)
            }
        }
        getCategoryList(binding.spinnerCategory.selectedItemPosition)
        binding.fabGotoMap.setOnClickListener {
//            cfm.beginTransaction().remove(this).commit()
            cfm.popBackStack()
            listener.onGotoMapClicked()
        }


    }

    private fun setSpinner() {
        val spinnerItem = resources.getStringArray(R.array.spinner_category)

        context?.let {
            val arrayAdapter = ArrayAdapter(
                it,
                R.layout.mate_status_spinner_item,
                spinnerItem
            )
            binding.spinnerCategory.adapter = arrayAdapter
        }

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(!mLoadingDialog.isShowing) getCategoryList(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun getCategoryList(order: Int) {
        context?.let {
            showLoadingDialog(it)
            CategoryListService(this).tryGetStoreCategoryList(getUserIdx(), getGroupIdx(), categoryIdx, order)
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
        if(!mLoadingDialog.isShowing) getCategoryList(binding.spinnerCategory.selectedItemPosition)
    }

    override fun onGetCategoryListSuccess(response: StoreCategoryListResponse) {
        dismissLoadingDialog()
        binding.llNoCategoryResult.isVisible = false
        storeListAdapter.removeAllData()
        storeListAdapter.addAllData(response.result.getStoresRes)

    }

    override fun onGetCategoryListFail(code : Int, message: String?) {
        dismissLoadingDialog()
        if(code == 2502){
            storeListAdapter.removeAllData()
            binding.llNoCategoryResult.isVisible = true
        }else {
            binding.llNoCategoryResult.isVisible = false
            showCustomToast(message?:resources.getString(R.string.failed_connection))
        }

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
        val dialog = StoreToMateSuggestDialog(requireContext(), this, storeName, storeImg)
        dialog.show()
    }

    override fun onLikeClicked(storeIdx: Int, isLiked : Boolean) {
        if(isLiked) LikeService(this).tryPostLike(getUserIdx(),storeIdx)
        else LikeService(this).tryPatchLike(getUserIdx(),storeIdx)
    }

    override fun onGotoMateSuggestClicked(storeName: String, storeImg: String) {
        context?.let {
            val intent = Intent(it, MateSuggestionActivity::class.java)
            intent.apply {
                putExtra("storeName", storeName)
                putExtra("storeImg", storeImg)
            }
            startActivity(intent)
        }
    }

    override fun onPostLikeSuccess() {

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