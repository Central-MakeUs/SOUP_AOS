package com.example.eatoo.src.review.store_map.kakao_api

import com.example.eatoo.BuildConfig
import com.example.eatoo.reverse_geo.ReverseGeoRetrofitInterface
import com.example.eatoo.src.review.store_location.StoreLocationRetrofitInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KakaoRetrofit {

    val kakaoAddressService: ReverseGeoRetrofitInterface by lazy { getRetrofit().create(
        ReverseGeoRetrofitInterface::class.java) }

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