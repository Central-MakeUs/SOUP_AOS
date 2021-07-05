package com.example.eatoo.src.main.create_group

import com.example.googlemapsapiprac.response.address.AddressInfoResponse

interface CreateGroupView {

    fun onGetCurrentAddressSuccess(response : AddressInfoResponse)

    fun onGetCurrentAddressFail(message : String?)
}