package com.example.eatoo.src.home.create_group

import com.example.eatoo.src.home.create_group.model.CreateGroupResponse

interface CreateGroupView {
    fun onPostGroupSuccess(response : CreateGroupResponse)
    fun onPostGroupFail(message : String?)
}