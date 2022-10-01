package com.harshit.kotlin_mvvm_retrofit_coroutines.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()

    suspend fun getUserDetails(id: String) = apiService.getUserDetails(id)

    suspend fun getUserPostDetails(id: Any) = apiService.getUserPostDetails(id)
}