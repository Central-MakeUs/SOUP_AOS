package com.makeus.eatoo.src.wishlist

import com.makeus.eatoo.src.home.group.category.category_list.model.StoreCategoryListResponse
import com.makeus.eatoo.src.wishlist.model.WishListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WishListRetrofitInterface {

    @GET("/app/stores/{userIdx}/{storeCategoryIdx}/liked")
    fun getWishList(
        @Path("userIdx") userIdx : Int,
        @Path("storeCategoryIdx") storeCategoryIdx : Int,
        @Query("order") order : Int
    ) : Call<WishListResponse>

}