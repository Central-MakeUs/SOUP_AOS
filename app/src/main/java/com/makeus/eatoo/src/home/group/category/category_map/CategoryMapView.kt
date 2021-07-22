package com.makeus.eatoo.src.home.group.category.category_map

import com.makeus.eatoo.src.home.group.category.category_map.model.CategoryMapResponse

interface CategoryMapView {
    fun onGetCategoryMapStoreSuccess(response: CategoryMapResponse)
    fun onGetCategoryMapStoreFail(message : String?)
}