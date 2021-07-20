package com.example.eatoo.src.home.create_group

import com.example.eatoo.src.home.create_group.model.CreateGroupRequest
import com.example.eatoo.src.home.create_group.model.CreateGroupResponse
import retrofit2.Call
import retrofit2.http.*

interface CreateGroupRetrofitInterface {

    @POST("/app/groups/{userIdx}")
    fun postGroup(
        @Body createGroupRequest: CreateGroupRequest,
        @Path("userIdx") userIdx: Int
    ) : Call<CreateGroupResponse>

}