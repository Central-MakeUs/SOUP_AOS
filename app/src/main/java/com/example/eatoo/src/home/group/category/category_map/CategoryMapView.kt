package com.example.eatoo.src.home.group.category.category_map

import com.example.eatoo.src.home.group.category.category_map.model.CategoryMapResponse

interface CategoryMapView {
    fun onGetCategoryMapStoreSuccess(response: CategoryMapResponse)
    fun onGetCategoryMapStoreFail(message : String?)
}