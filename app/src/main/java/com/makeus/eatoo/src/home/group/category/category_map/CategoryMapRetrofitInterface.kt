package com.makeus.eatoo.src.home.group.category.category_map

import com.makeus.eatoo.src.home.group.category.category_map.model.CategoryMapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryMapRetrofitInterface {
    @GET("/app/groups/{userIdx}/category")
    fun getCategoryMapStore(
        @Path("userIdx") userIdx : Int
    ) :Call<CategoryMapResponse>
}