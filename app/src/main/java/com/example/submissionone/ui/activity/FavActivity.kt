package com.example.submissionone.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionone.adapter.FavoriteAdapter
import com.example.submissionone.adapter.UserAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ActivityFavBinding
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.ui.model.FavViewModel
import com.example.submissionone.ui.model.FavViewModelFactory
import com.example.submissionone.ui.model.MainViewModel

class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavBinding
    private val favAdapter: FavoriteAdapter = FavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Favorite"
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFav.addItemDecoration(itemDecoration)

        val factory: FavViewModelFactory = FavViewModelFactory.getDatabase(application)
        val favViewModel: FavViewModel by viewModels {
            factory
        }

        favViewModel.isLoading.observe(this){loading ->
            showLoading(loading)
        }

        favViewModel.favoriteUsers.observe(this){favorite ->
            setFavoriteData(favorite)
        }

        favViewModel.getFavoriteUsers()
    }

    private fun setFavoriteData(favorite: List<FavEntity>) {
        favAdapter.submitList(favorite)
        binding.rvFav.adapter = favAdapter
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}