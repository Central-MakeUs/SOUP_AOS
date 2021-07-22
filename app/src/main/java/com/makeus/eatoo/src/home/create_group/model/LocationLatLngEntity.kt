package com.makeus.googlemapsapiprac.model

import android.os.Parcel
import android.os.Parcelable


data class LocationLatLngEntity(
    val latitude : Float,
    val longitude : Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(latitude)
        parcel.writeFloat(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationLatLngEntity> {
        override fun createFromParcel(parcel: Parcel): LocationLatLngEntity {
            return LocationLatLngEntity(parcel)
        }

        override fun newArray(size: Int): Array<LocationLatLngEntity?> {
            return arrayOfNulls(size)
        }
    }
}
