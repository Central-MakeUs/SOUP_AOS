package com.example.eatoo.src.home.group.category

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupCategoryBinding
import com.example.eatoo.databinding.FragmentGroupMainBinding
import com.example.eatoo.src.home.group.category.category_map.CategoryMapService
import com.example.eatoo.src.home.group.category.category_map.CategoryMapView
import com.example.eatoo.src.home.group.category.category_map.model.CategoryMapResponse
import com.example.eatoo.util.getUserIdx

class GroupCategoryFragment
    : BaseFragment<FragmentGroupCategoryBinding>(FragmentGroupCategoryBinding::bind, R.layout.fragment_group_category),
CategoryMapView{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategoryMapStore()
    }

    private fun getCategoryMapStore() {
        context?.let {
            showLoadingDialog(it)
            CategoryMapService(this).tryGetCategoryMapStore(getUserIdx())
        }

    }

    override fun onGetCategoryMapStoreSuccess(response: CategoryMapResponse) {
        dismissLoadingDialog()
        Log.d("groupCategoryFragment", response.toString())
    }

    override fun onGetCategoryMapStoreFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}