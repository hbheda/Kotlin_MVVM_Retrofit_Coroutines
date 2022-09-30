package com.harshit.kotlin_mvvm_retrofit_coroutines.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.api.ApiHelper
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.repository.MainRepository
import com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}