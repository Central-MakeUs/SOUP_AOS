package com.example.eatoo.util

import com.example.googlemapsapiprac.response.search.Poi

fun makeMainAdress(poi: Poi): String =
    if (poi.secondNo?.trim().isNullOrEmpty()) {
        (poi.upperAddrName?.trim() ?: "") + " " +
                (poi.middleAddrName?.trim() ?: "") + " " +
                (poi.lowerAddrName?.trim() ?: "") + " " +
                (poi.detailAddrName?.trim() ?: "") + " " +
                poi.firstNo?.trim()
    } else {
        (poi.upperAddrName?.trim() ?: "") + " " +
                (poi.middleAddrName?.trim() ?: "") + " " +
                (poi.lowerAddrName?.trim() ?: "") + " " +
                (poi.detailAddrName?.trim() ?: "") + " " +
                (poi.firstNo?.trim() ?: "") + " " +
                poi.secondNo?.trim()
    }