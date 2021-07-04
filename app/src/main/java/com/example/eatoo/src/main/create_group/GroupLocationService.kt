package com.example.eatoo.src.main.create_group

import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.googlemapsapiprac.response.search.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupLocationService(val view: GroupLocationActivity) {

    fun tryGetLocation(keyword: String) {
        val groupLocationRetrofitInterface = ApplicationClass.sRetrofit.create(
            GroupLocationRetrofitInterface::class.java
        )
        groupLocationRetrofitInterface.getSearchLocation(keyword = keyword).enqueue(object :
            Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.onGetLocationSuccess(response.body() as SearchResponse)
                    }
                } else {
                    view.onGetLocationFail(ApplicationClass.applicationResources.getString(R.string.search_location_fail))
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                view.onGetLocationFail(ApplicationClass.applicationResources.getString(R.string.search_location_fail))
            }

        })
    }
}