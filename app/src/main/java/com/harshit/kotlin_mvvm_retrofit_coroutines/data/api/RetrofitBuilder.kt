package com.harshit.kotlin_mvvm_retrofit_coroutines.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.connection.ConnectInterceptor.intercept
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://e24a4c1c-9008-4848-ad95-bf906d5c3e67.mock.pstmn.io/"

    var logging  = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    var  httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(logging);


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}