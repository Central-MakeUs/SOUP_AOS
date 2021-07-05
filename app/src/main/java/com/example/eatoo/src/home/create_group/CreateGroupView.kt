package com.example.eatoo.src.home.create_group

import com.example.googlemapsapiprac.response.address.AddressInfoResponse

interface CreateGroupView {

    fun onGetCurrentAddressSuccess(response : AddressInfoResponse)

    fun onGetCurrentAddressFail(message : String?)
}