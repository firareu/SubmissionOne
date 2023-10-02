package com.example.submissionone.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionone.data.retrofit.NetworkConnection
import com.example.submissionone.R
import com.example.submissionone.adapter.SectionsPagerAdapter
import com.example.submissionone.adapter.UserFollowAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ActivityDetailBinding
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.ui.model.DetailViewModel
import com.example.submissionone.ui.model.DetailViewModelFactory
import com.example.submissionone.ui.model.FavViewModel
import com.example.submissionone.ui.model.FavViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userFollowAdapter: UserFollowAdapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userFollowAdapter = UserFollowAdapter()
        val userdata = intent.getParcelableExtra<UserResponse>(KEY_USER)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            if (userdata != null) {
                supportActionBar?.title = userdata.login
            }
        }

        if (userdata != null) {
            username = userdata.login
            username?.let {
                checkInternetConnection(it)
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun darkmode(apakah: Boolean){
        //binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun brightmode(apakah: Boolean){
        //binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkInternetConnection(username:  String) {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
                val detailViewModel: DetailViewModel by viewModels {
                    DetailViewModelFactory(username)
                }

                detailViewModel.isLoading.observe(this, { isLoading ->
                    showProgressBar(isLoading)
                })

                detailViewModel.detailUser.observe(this, { userResponse ->
                    if (userResponse != null) {
                        showProgressBar(false)
                        Toast.makeText(this, userResponse.login, Toast.LENGTH_LONG).show()
                        setData(userResponse)
                        initViewPagerAndTabs(userResponse)

                        // fav klik
                        val factory: FavViewModelFactory = FavViewModelFactory.getDatabase(application)
                        val viewModel: FavViewModel by viewModels {
                            factory
                        }
                        val fabFavorite = binding.ivBookmark

                        // Pastikan viewModel.isFavorite telah diinisialisasi sebelum mengaksesnya
                        viewModel.isFavorite.observe(this) { isFavorite ->
                            Toast.makeText(this, "keklik ko bg", Toast.LENGTH_LONG).show()
                            if (isFavorite != null) {
                                if (isFavorite) {
                                    fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                                } else {
                                    fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                                }
                            }
                        }
                        username?.let {
                            viewModel.checkIsFavorite(it)
                        }
                        fabFavorite.setOnClickListener {
                            //Toast.makeText(this, "keklik ko bg listenernya", Toast.LENGTH_LONG).show()
                            username?.let { username ->
                                val isCurrentlyFavorite = viewModel.isFavorite.value ?: false
                                if (isCurrentlyFavorite == true) {
                                    val favEntity = FavEntity(username, userResponse.avatarUrl, userResponse.htmlUrl, false)
                                    viewModel.removeFavoriteUser(favEntity)
                                } else {
                                    val favEntity = FavEntity(username, userResponse.avatarUrl, userResponse.htmlUrl, true)
                                    viewModel.addFavoriteUser(favEntity)
                                }
                            }
                        }


                    } else {
                        // Handle the case when userResponse is null
                    }
                })
            } else {
                binding.dataLayout.visibility = View.GONE
            }
        })
    }

    private fun initViewPagerAndTabs(user: UserResponse) {
        val sectionPagerAdapter = SectionsPagerAdapter(this, user)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("StringFormatMatches")
    private fun setData(userResponse: UserResponse) {
        with(binding) {
            dataLayout.visibility = View.VISIBLE
            Glide.with(root)
                .load(userResponse.avatarUrl)
                .circleCrop()
                .into(profileImage)
            detailsTvName.text = userResponse.name
            detailsTvUsername.text = userResponse.login
            val follower = userResponse.followers?.toIntOrNull() ?: 0
            val followerText = getString(R.string.followerNum, follower)
            detailsTvFollower.text = followerText
            val following = userResponse.following?.toIntOrNull() ?: 0
            val followingText = getString(R.string.followingNum, following)
            detailsTvFollowing.text = followingText
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_USER = "user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}
