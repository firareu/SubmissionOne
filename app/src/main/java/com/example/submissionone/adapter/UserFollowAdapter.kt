package com.example.submissionone.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ItemSearchBinding

class UserFollowAdapter :
    RecyclerView.Adapter<UserFollowAdapter.MyViewHolder>() {
    private var listUserResponse = ArrayList<UserResponse>()

    fun addDataToList(items: ArrayList<UserResponse>) {
        listUserResponse.clear()
        listUserResponse.addAll(items)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: ViewAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ViewAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUserResponse[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUserResponse[position])
        }
    }

    override fun getItemCount() = listUserResponse.size

    class MyViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userResponse: UserResponse) {
            binding.textViewName.text = userResponse.login
            Glide.with(binding.root)
                .load(userResponse.avatarUrl)
                .apply(RequestOptions().override(500, 500))
                .circleCrop()
                .into(binding.imgItemPhoto)
            binding.textViewUrl.text = userResponse.htmlUrl
        }
    }
}
