package com.makeus.eatoo.like

import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.like.model.LikeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeService(val view : LikeView) {

    fun tryPostLike(userIdx : Int, groupIdx : Int) {
        val likeRetrofitInterface = ApplicationClass.sRetrofit.create(
            LikeRetrofitInterface::class.java)
        likeRetrofitInterface.postLike(userIdx, groupIdx).enqueue(object :
            Callback<LikeResponse> {
            override fun onResponse(
                call: Call<LikeResponse>,
                response: Response<LikeResponse>
            ) {
                response.body()?.let {
                    if(it.isSuccess) view.onPostLikeSuccess()
                    else view.onPostLikeFail(it.message)
                }

            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                view.onPostLikeFail(t.message?:ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }

    fun tryPatchLike(userIdx : Int, groupIdx : Int) {
        val likeRetrofitInterface = ApplicationClass.sRetrofit.create(
            LikeRetrofitInterface::class.java)
        likeRetrofitInterface.patchLike(userIdx, groupIdx).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                response.body()?.let {
                    if(it.isSuccess) view.onPatchLikeSuccess()
                    else view.onPatchLikeFail(it.message)
                }

            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchLikeFail(t.message?:ApplicationClass.applicationResources.getString(R.string.failed_db_connection))
            }

        })
    }
}