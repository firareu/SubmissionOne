package com.example.submissionone.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.example.submissionone.NetworkConnection
import com.example.submissionone.R
import com.example.submissionone.adapter.ViewAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ActivityMainBinding
import com.example.submissionone.ui.model.MainViewModel

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val viewAdapter: ViewAdapter = ViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSearchView()
        checkInternetConnection()
        //supportActionBar?.hide()
    }



    private fun setUpSearchView() {
        with(binding) {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.getSearchUser(query)
                    mainViewModel.searchuser.observe(this@MainActivity) { searchUserResponse ->
                        if (searchUserResponse != null) {
                            viewAdapter.submitList(searchUserResponse)
                            setUserData()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun checkInternetConnection() {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                mainViewModel.user.observe(this, { userResponse ->
                    if (userResponse != null) {
                        viewAdapter.submitList(userResponse)
                        setUserData()
                    }
                })
                mainViewModel.searchuser.observe(this@MainActivity) { searchUserResponse ->
                    if (searchUserResponse != null) {
                        viewAdapter.submitList(searchUserResponse)
                        binding.rvSearch.visibility = View.VISIBLE
                    }
                }
            } else {
                mainViewModel.user.observe(this, { userResponse ->
                    if (userResponse != null) {
                        viewAdapter.submitList(userResponse)
                        setUserData()
                    }
                })
                mainViewModel.searchuser.observe(this@MainActivity) { searchUserResponse ->
                    if (searchUserResponse != null) {
                        viewAdapter.submitList(searchUserResponse)
                        binding.rvSearch.visibility = View.VISIBLE
                    }
                }
                Toast.makeText(this@MainActivity, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        })
    }
    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_apps, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_profile -> {
                val btnprofile = Intent(this, DetailActivity::class.java)
                startActivity(btnprofile)
                //Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hideUserList() {
        binding.rvSearch.layoutManager = null
        binding.rvSearch.adapter = null
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }


    private fun setUserData() {
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvSearch.layoutManager = layoutManager
        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.adapter = viewAdapter
        viewAdapter.setOnItemClickCallback(object : ViewAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                hideUserList()
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.KEY_USER, data)
                startActivity(intent)
            }
        })
    }



}