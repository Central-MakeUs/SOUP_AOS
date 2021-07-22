package com.makeus.eatoo.src.home.create_group.group_location

import com.makeus.googlemapsapiprac.response.search.SearchResponse

interface GroupLocationView {
    fun onGetLocationSuccess(response : SearchResponse)

    fun onGetLocationFail(message : String?)
}