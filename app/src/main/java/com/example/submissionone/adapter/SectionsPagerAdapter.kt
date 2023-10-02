package com.example.submissionone.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.ui.fragment.FollowerFragment
import com.example.submissionone.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, user: UserResponse) : FragmentStateAdapter(activity) {
    private var userResponse: UserResponse = user

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFragment.newInstance(position, userResponse)
            1 -> FollowingFragment.newInstance(position, userResponse)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
