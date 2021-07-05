package com.example.googlemapsapiprac.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationLatLngEntity(
    val latitude : Float,
    val longitude : Float
) : Parcelable
