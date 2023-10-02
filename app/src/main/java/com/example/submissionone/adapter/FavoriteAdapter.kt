package com.example.submissionone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionone.R
import com.example.submissionone.databinding.ItemUserBinding
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.ui.activity.DetailActivity
import com.example.submissionone.ui.activity.DetailActivity.Companion.EXTRA_USERNAME

class FavoriteAdapter: androidx.recyclerview.widget.ListAdapter<FavEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
        holder.itemView.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, favorite.username)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavEntity) {
            binding.textViewName.text = "${user.username}"
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.textViewUrl.text = user.htmlUrl
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavEntity>() {
            override fun areItemsTheSame(oldItem: FavEntity, newItem: FavEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavEntity, newItem: FavEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}