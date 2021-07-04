package com.example.eatoo.src.main.create_group

import com.example.googlemapsapiprac.response.search.SearchResponse

interface GroupLocationView {
    fun onGetLocationSuccess(response : SearchResponse)

    fun onGetLocationFail(message : String?)
}