package com.harshit.kotlin_mvvm_retrofit_coroutines.data.repository

import com.harshit.kotlin_mvvm_retrofit_coroutines.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

    suspend fun getUserDetails(id: String) = apiHelper.getUserDetails(id)

    suspend fun getUserPostDetails(id: Any) = apiHelper.getUserPostDetails(id)

}