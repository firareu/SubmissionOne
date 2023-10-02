package com.example.submissionone.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ItemUserBinding
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.ui.activity.DetailActivity

class FavoriteAdapter: androidx.recyclerview.widget.ListAdapter<FavEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
        holder.itemView.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)

            // Memeriksa apakah favorite.username tidak null atau kosong sebelum mengirimkannya
            if (favorite.username.isNotEmpty()) {
                intent.putExtra(DetailActivity.KEY_USER, favorite.username)
                Log.d("FavoriteAdapter", "Favorite username: ${favorite.username}")
                Log.d("FavoriteAdapter", "Favorite username: ${context}")
                Log.d("FavoriteAdapter", "Favorite username: ${context.startActivity(intent)}")
                context.startActivity(intent)
            } else {
                Log.e("FavoriteAdapter", "Favorite username is null or empty")
                // Handle kasus di mana username null atau kosong (tampilkan pesan kesalahan jika perlu)
            }
        }
    }

    class MyViewHolder(val binding: ItemUserBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavEntity) {
            binding.textViewName.text = user.username
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