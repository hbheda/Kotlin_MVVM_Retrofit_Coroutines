package com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.api.ApiHelper
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.api.RetrofitBuilder
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.model.User
import com.harshit.kotlin_mvvm_retrofit_coroutines.databinding.ActivityMainBinding
import com.harshit.kotlin_mvvm_retrofit_coroutines.ui.base.ViewModelFactory
import com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.adapter.MainAdapter
import com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.viewmodel.MainViewModel
import com.harshit.kotlin_mvvm_retrofit_coroutines.utils.Status.ERROR
import com.harshit.kotlin_mvvm_retrofit_coroutines.utils.Status.LOADING
import com.harshit.kotlin_mvvm_retrofit_coroutines.utils.Status.SUCCESS

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<User>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }
}