package com.harshit.kotlin_mvvm_retrofit_coroutines.data.api

import com.harshit.kotlin_mvvm_retrofit_coroutines.data.model.User
import retrofit2.http.*
import java.util.Objects

interface ApiService {

    @GET("getUsersList")
    suspend fun getUsers(): List<User>

    @GET("getUserDetails/{id}")
    suspend fun getUserDetails(@Path("id") id: String): User

    @Headers("Content-Type: application/json")
    @POST("getUserPostDetails")
    suspend fun getUserPostDetails(@Body id: Any): User

}