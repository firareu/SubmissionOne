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
import com.example.submissionone.data.retrofit.NetworkConnection
import com.example.submissionone.R
//import com.example.submissionone.adapter.ListUserAdapter
import com.example.submissionone.adapter.UserAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ActivityMainBinding
import com.example.submissionone.ui.model.FavViewModel
import com.example.submissionone.ui.model.MainViewModel
import com.example.submissionone.local.Result
import com.example.submissionone.ui.model.FavViewModelFactory

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val userAdapter: UserAdapter = UserAdapter()
    private val favViewModel by viewModels<FavViewModel>{
        FavViewModelFactory.getDatabase(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSearchView()
        checkInternetConnection()

//        val userAdapter = ListUserAdapter{user ->
//            if (user.isFavorite){
//                favViewModel.deleteFavorite(user)
//                Toast.makeText(this, "Berhasil Menghapus ${user.login} dari favorite", Toast.LENGTH_SHORT).show()
//            }else{
//                favViewModel.saveFavorite(user)
//                Toast.makeText(this, "Berhasil Menambah ${user.login} ke favorite", Toast.LENGTH_SHORT).show()
//            }
//        }
//        favViewModel.getAllUser().observe(this) { result ->
//                when (result) {
//                    is Result.Loading -> {
//                        favViewModel.loading.observe(this){
//                            showLoading(it)
//                        }
//                    }
//
//                    is Result.Success -> {
//                        favViewModel.loading.observe(this){
//                            showLoading(it)
//                        }
//                        val userData = result.data
//                        userAdapter.submitList(userData)
//
//                    }
//
//                    is Result.Error -> {
//                        favViewModel.loading.observe(this){
//                            showLoading(it)
//                        }
//                        Toast.makeText(
//                            this,
//                            "terjadi kesalahan" + result.error, Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//        }
//
//        binding.rvSearch.apply {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = userAdapter
//        }
    }

    private fun setUpSearchView() {
        with(binding) {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.getSearchUser(query)
                    showLoading(true)
                    mainViewModel.searchuser.observe(this@MainActivity) { searchUserResponse ->
                        if (searchUserResponse != null) {
                            userAdapter.submitList(searchUserResponse)
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
        showLoading(true)
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                mainViewModel.user.observe(this, { userResponse ->
                    if (userResponse != null) {
                        userAdapter.submitList(userResponse)
                        setUserData()
                        showLoading(false)
                    }
                })
                mainViewModel.searchuser.observe(this@MainActivity) { searchUserResponse ->
                    if (searchUserResponse != null) {
                        userAdapter.submitList(searchUserResponse)
                        binding.rvSearch.visibility = View.VISIBLE
                        showLoading(false)
                    }
                }

            } else {
                mainViewModel.user.observe(this, { userResponse ->
                    if (userResponse != null) {
                        userAdapter.submitList(userResponse)
                        setUserData()
                        showLoading(false)
                    }
                })
                mainViewModel.searchuser.observe(this@MainActivity) { searchUserResponse ->
                    if (searchUserResponse != null) {
                        userAdapter.submitList(searchUserResponse)
                        binding.rvSearch.visibility = View.VISIBLE
                        showLoading(false)
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
            R.id.action_fav -> {
                val intentfav = Intent(this, FavActivity::class.java)
                //intent.putParcelableArrayListExtra(FavActivity.EXTRA_FAV_USERS, ArrayList(userAdapter.getFavList()))
                startActivity(intentfav)
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
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }


    private fun setUserData() {
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvSearch.layoutManager = layoutManager
        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                hideUserList()
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.KEY_USER, data)
                startActivity(intent)
            }
        })
    }
}