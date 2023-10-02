package com.example.submissionone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ItemUserBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val userList = ArrayList<UserResponse>()


    fun submitList(users: ArrayList<UserResponse>) {
        userList.clear()
        userList.addAll(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(userList[position]) }
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            binding.textViewName.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.textViewUrl.text = user.htmlUrl
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }
}