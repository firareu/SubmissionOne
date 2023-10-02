package com.example.submissionone.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionone.adapter.FavoriteAdapter
import com.example.submissionone.databinding.ActivityFavBinding
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.ui.model.FavViewModel
import com.example.submissionone.ui.model.FavViewModelFactory

class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavBinding
//    private val adapter by lazy {
//        UserAdapter {user ->
//            Intent(this@FavActivity, DetailActivity::class.java).apply {
//                //putExtra("item", user)
//                startActivity(this)
//            }
//        }
//    }

    /*private val dbModule by lazy { DbModule(this) }
    private val favViewModel by viewModels<FavViewModel>  {
        FavViewModelFactory(dbModule)
    }*/

    override fun onResume() {
        //favViewModel.getUserFavorite().observe(this) {
            //adapter.submitList(it)
        //}
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Setting"
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
        val adapter = FavoriteAdapter()
        adapter.submitList(favorite)
        binding.rvFav.adapter = adapter
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object{}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}