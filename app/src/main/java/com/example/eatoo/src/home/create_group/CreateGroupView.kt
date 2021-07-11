package com.example.eatoo.src.home.create_group

import com.example.eatoo.src.home.create_group.model.CreateGroupResponse
import com.example.eatoo.src.home.create_group.model.address.AddressInfoResponse

interface CreateGroupView {

    fun onGetCurrentAddressSuccess(response : AddressInfoResponse)

    fun onGetCurrentAddressFail(message : String?)

    fun onPostGroupSuccess(response : CreateGroupResponse)

    fun onPostGroupFail(message : String?)
}