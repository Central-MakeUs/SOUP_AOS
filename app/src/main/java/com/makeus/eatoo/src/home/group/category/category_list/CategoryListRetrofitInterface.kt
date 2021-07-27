package com.makeus.eatoo.src.home.group.category.category_list

import com.makeus.eatoo.src.home.group.category.category_list.model.StoreCategoryListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryListRetrofitInterface {

    @GET("/app/groups/{userIdx}/{groupIdx}/{storeCategoryIdx}/category-list")
    fun getStoreCategoryList(
        @Path ("userIdx") userIdx : Int,
        @Path ("groupIdx") groupIdx : Int,
        @Path ("storeCategoryIdx") storeCategoryIdx : Int,
        @Query ("order") order : Int
    ) : Call<StoreCategoryListResponse>
}