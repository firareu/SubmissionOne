package com.example.submissionone.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissionone.NetworkConnection
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ActivityDetailBinding
import com.example.submissionone.ui.model.DetailViewModel
import com.example.submissionone.ui.model.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userdata = intent.getParcelableExtra<UserResponse>(KEY_USER)
        if (userdata != null) {
            userdata.login?.let{
                checkInternetConnection(it)
            }
                Toast.makeText(this, userdata.name, Toast.LENGTH_LONG).show()
                with(binding) {
                    dataLayout.visibility = View.VISIBLE
                    Glide.with(root)
                        .load(userdata.avatarUrl)
                        .circleCrop()
                        .into(profileImage)
                    detailsTvName.text = userdata.name
                    detailsTvUsername.text = userdata.login
                    detailsTvFollower.text = userdata.htmlUrl
                    detailsTvFollowing.text = userdata.following
                }

        }
    }

    private fun checkInternetConnection(username: String) {
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
                        setData(userResponse)
                    } else {
                        // Handle the case when userResponse is null
                    }
                })
            } else {
                binding.dataLayout.visibility = View.GONE
            }
        })
    }

    private fun setData(userResponse: UserResponse) {
        with(binding) {
            dataLayout.visibility = View.VISIBLE
            Glide.with(root)
                .load(userResponse.avatarUrl)
                .into(profileImage)
            detailsTvName.text = userResponse.name
            detailsTvUsername.text = userResponse.login
            detailsTvFollower.text = userResponse.followers
            detailsTvFollowing.text = userResponse.following
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY_USER = "user"
        const val EXTRA_USERNAME = "username"
    }
}
