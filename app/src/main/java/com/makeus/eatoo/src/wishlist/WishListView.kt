package com.makeus.eatoo.src.wishlist

import com.makeus.eatoo.src.wishlist.model.WishListResponse

interface WishListView {
    fun onGetWishListSuccess(response: WishListResponse)
    fun onGetWishListFail(code : Int, message : String?)
}