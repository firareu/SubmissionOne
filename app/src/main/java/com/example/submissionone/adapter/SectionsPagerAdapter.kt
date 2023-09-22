package com.example.submissionone.adapter

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.ui.fragment.FollowerFragment
import com.example.submissionone.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        //fragment?.arguments = login
        return fragment as Fragment
    }

    /*var model: UserResponse? = null
    @RequiresApi(Build.VERSION_CODES.P)
    override fun createFragment(position: Int): Fragment {
        return FollowerFragment.newInstance(position + 1 , model)
    }*/

    override fun getItemCount(): Int {
        return 2
    }

}