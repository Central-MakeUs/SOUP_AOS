package com.makeus.eatoo.src.home.create_group

import com.makeus.eatoo.src.home.create_group.model.CreateGroupRequest
import com.makeus.eatoo.src.home.create_group.model.CreateGroupResponse
import retrofit2.Call
import retrofit2.http.*

interface CreateGroupRetrofitInterface {

    @POST("/app/groups/{userIdx}")
    fun postGroup(
        @Body createGroupRequest: CreateGroupRequest,
        @Path("userIdx") userIdx: Int
    ) : Call<CreateGroupResponse>

}