package com.example.eatoo.src.review.store_location

import com.example.eatoo.src.review.store_location.model.KakaoSearchResponse
import com.example.eatoo.src.review.store_map.kakao_api.KakaoRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreLocationService(val view : StoreLocationView) {

    fun tryGetStoreSearch(query : String?, page : Int) {
        KakaoRetrofit.kakaoSearchService.getStoreSearch(query = query, page = page).enqueue(object :
            Callback<KakaoSearchResponse> {
            override fun onResponse(
                call: Call<KakaoSearchResponse>,
                response: Response<KakaoSearchResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.onSearchStoreSuccess(response.body() as KakaoSearchResponse)
                    }
                } else {
                    view.onSearchStoreFail(response.message())
                }
            }
            override fun onFailure(call: Call<KakaoSearchResponse>, t: Throwable) {
                view.onSearchStoreFail(t.message?:"통신오류가 발생했습니다.")
            }

        })
    }
}