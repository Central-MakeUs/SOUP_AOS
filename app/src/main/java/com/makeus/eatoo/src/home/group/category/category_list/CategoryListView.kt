package com.makeus.eatoo.src.home.group.category.category_list

import com.makeus.eatoo.src.home.group.category.category_list.model.StoreCategoryListResponse

interface CategoryListView {
    fun onGetCategoryListSuccess(response : StoreCategoryListResponse)
    fun onGetCategoryListFail(message : String?)
}