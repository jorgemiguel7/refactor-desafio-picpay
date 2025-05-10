package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val userListAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        observeViewModel()
        viewModel.fetchUsers()
    }

    private fun initView() = with(binding) {
        userListProgressBar.visibility = View.VISIBLE
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
        }
    }

    private fun observeViewModel() = with(viewModel) {
        isLoading.observe(this@MainActivity) { isLoading ->
            binding.userListProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        error.observe(this@MainActivity) { error ->
            if (error) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        users.observe(this@MainActivity) { users ->
            userListAdapter.submitList(users)
        }
    }
}
