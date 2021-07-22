package com.example.eatoo.src.review.store_map.kakao_api

import com.example.eatoo.BuildConfig
import com.example.eatoo.src.home.create_group.CreateGroupRetrofitInterface
import com.example.eatoo.src.home.create_group.api_util.TmapUrl
import com.example.eatoo.src.review.store_location.StoreLocationRetrofitInterface
import com.example.eatoo.src.review.store_map.StoreMapRetrofitInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KakaoRetrofit {

    val kakaoAddressService: StoreMapRetrofitInterface by lazy { getRetrofit().create(
        StoreMapRetrofitInterface::class.java) }

    val kakaoSearchService : StoreLocationRetrofitInterface by lazy { getRetrofit().create(
        StoreLocationRetrofitInterface::class.java
    ) }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(KakaoUrl.KAKAO_URL)
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