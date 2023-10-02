package com.example.submissionone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionone.R
import com.example.submissionone.adapter.UserFollowAdapter
import com.example.submissionone.adapter.ViewAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.FragmentFollowerBinding
import com.example.submissionone.ui.activity.DetailActivity
import com.example.submissionone.ui.model.FollowViewModel
import com.example.submissionone.ui.model.FollowViewModelFactory

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding: FragmentFollowerBinding
        get() = _binding ?: throw IllegalStateException("ViewBinding not initialized")

    private lateinit var userFollowAdapter: UserFollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userFollowAdapter = UserFollowAdapter()

        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        val user = arguments?.getParcelable<UserResponse>(ARG_PARCEL)
        if (index == 0) {
            user?.login?.let {
                setViewModel(it, index)
            }
        }
    }

    private fun setViewModel(username: String, index: Int) {
        val followViewModel: FollowViewModel by viewModels {
            FollowViewModelFactory(username)
        }
        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
        if (index == 0) {
            followViewModel.followers.observe(viewLifecycleOwner, { followers ->
                if (followers != null) {
                    setUserData(followers)
                }
            })
        }
    }

    private fun setUserData(userResponse: ArrayList<UserResponse>) {
        if (userResponse.isNotEmpty()) {
            userFollowAdapter.addDataToList(userResponse)
            with(binding) {
                val layoutManager = LinearLayoutManager(view?.context)
                rvFollower.layoutManager = layoutManager
                rvFollower.adapter = userFollowAdapter
                userFollowAdapter.setOnItemClickCallback(object : ViewAdapter.OnItemClickCallback {
                    override fun onItemClicked(user: UserResponse) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.KEY_USER, user)
                        startActivity(intent)
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_PARCEL = "user_model"

        @JvmStatic
        fun newInstance(sectionNumber: Int, user: UserResponse): FollowerFragment {
            val fragment = FollowerFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            args.putParcelable(ARG_PARCEL, user)
            fragment.arguments = args
            return fragment
        }
    }
}


