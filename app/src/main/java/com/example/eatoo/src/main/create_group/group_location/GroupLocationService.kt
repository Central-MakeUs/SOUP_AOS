package com.example.eatoo.src.main.create_group.group_location

import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.src.main.create_group.api_util.TmapRetrofit
import com.example.googlemapsapiprac.response.search.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupLocationService(val view: GroupLocationActivity) {

    fun tryGetLocation(keyword: String) {

        TmapRetrofit.locationService.getSearchLocation(keyword = keyword).enqueue(object :
            Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    //Log.d("tmap", response.toString())
                    response.body()?.let {
                        view.onGetLocationSuccess(response.body() as SearchResponse)
                    }
                } else {
                   // Log.d("tmap", response.toString())
                    view.onGetLocationFail(ApplicationClass.applicationResources.getString(R.string.search_location_fail))
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                //Log.d("tmap", t.message.toString())
                view.onGetLocationFail(ApplicationClass.applicationResources.getString(R.string.search_location_fail))
            }

        })
    }
}