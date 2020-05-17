package com.cmedina.condorlabs.data.network

import com.cmedina.condorlabs.core.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitInstance: Retrofit by lazy {

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY


        httpClient.addInterceptor(logging)

        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

}