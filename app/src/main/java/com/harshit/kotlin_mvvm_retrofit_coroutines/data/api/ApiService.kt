package com.harshit.kotlin_mvvm_retrofit_coroutines.data.api

import com.harshit.kotlin_mvvm_retrofit_coroutines.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("getUsersList")
    suspend fun getUsers(): List<User>

}