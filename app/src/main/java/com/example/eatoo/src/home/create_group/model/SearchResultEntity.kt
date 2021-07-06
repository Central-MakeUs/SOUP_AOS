package com.example.googlemapsapiprac.model

import android.os.Parcel
import android.os.Parcelable

data class SearchResultEntity(
    val fullAddress: String,
    val buildingName: String,
    val locationLatLng: LocationLatLngEntity
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readParcelable(LocationLatLngEntity::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullAddress)
        parcel.writeString(buildingName)
        parcel.writeParcelable(locationLatLng, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResultEntity> {
        override fun createFromParcel(parcel: Parcel): SearchResultEntity {
            return SearchResultEntity(parcel)
        }

        override fun newArray(size: Int): Array<SearchResultEntity?> {
            return arrayOfNulls(size)
        }
    }
}
