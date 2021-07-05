package com.example.eatoo.src.home.create_group.api_util

import com.example.eatoo.BuildConfig
import com.example.eatoo.src.home.create_group.CreateGroupRetrofitInterface
import com.example.eatoo.src.home.create_group.group_location.GroupLocationRetrofitInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TmapRetrofit {

    val locationService: GroupLocationRetrofitInterface by lazy { getRetrofit().create(
        GroupLocationRetrofitInterface::class.java) }

    val addressService: CreateGroupRetrofitInterface by lazy { getRetrofit().create(
        CreateGroupRetrofitInterface::class.java) }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(TmapUrl.TMAP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
    }


    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }
}