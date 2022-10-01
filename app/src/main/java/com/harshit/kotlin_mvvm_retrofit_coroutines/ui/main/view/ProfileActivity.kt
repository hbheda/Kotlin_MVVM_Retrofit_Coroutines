package com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.api.ApiHelper
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.api.RetrofitBuilder
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.model.User
import com.harshit.kotlin_mvvm_retrofit_coroutines.databinding.ActivityProfileBinding
import com.harshit.kotlin_mvvm_retrofit_coroutines.ui.base.ViewModelFactory
import com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.viewmodel.MainViewModel
import com.harshit.kotlin_mvvm_retrofit_coroutines.utils.Status
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: MainViewModel
    private val TAG: String = ProfileActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_profile)
        setupViewModel()
        //setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers() {
        val userId = intent.getStringExtra("id")

        userId?.let {
            //val user: UserPost = UserPost(userId)
            val jsonObject: JSONObject = JSONObject().put("id",userId)
            viewModel.getUserPostDetails(jsonObject).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.linearLayout.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            resource.data?.let { user -> userDetails(user) }
                        }
                        Status.ERROR -> {
                            binding.linearLayout.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            Log.e(TAG, "Error-> " + it.message)
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.linearLayout.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    private fun userDetails(user: User) {

        binding.textViewUserName.text = user.name
        binding.textViewUserEmail.text = user.email
        Glide.with(binding.imageViewPhoto.context)
            .load(user.photo)
            .override(100,100)
            .into(binding.imageViewPhoto)
    }

    class UserPost constructor(
        id: String
    )
}