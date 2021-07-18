package com.example.eatoo.src.mypage.profile

import com.example.eatoo.src.mypage.profile.model.PatchProfileRequest
import com.example.eatoo.src.mypage.profile.model.PatchProfileResponse
import com.example.eatoo.src.mypage.profile.model.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ProfileRetrofitInterface {

    @GET("/app/users/{userIdx}/profile")
    fun getProfile(
        @Path("userIdx") userIdx : Int
    ) : Call<ProfileResponse>

    @PATCH("/app/users/{userIdx}/profile")
    fun patchProfile(
        @Path("userIdx") userIdx : Int,
        @Body patchProfileRequest: PatchProfileRequest
    ) : Call<PatchProfileResponse>
}