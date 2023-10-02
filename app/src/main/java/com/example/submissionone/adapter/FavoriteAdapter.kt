package com.example.submissionone.adapter

import android.annotation.SuppressLint
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

class FavoriteAdapter(private val onFavoriteClick : (FavEntity)->Unit) : ListAdapter<FavEntity, FavoriteAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        val ivBookmark = holder.binding.ivBookmark
        if (user.isFavorite){
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_favorite_24))
        }else{
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_favorite_border_24))
        }
        ivBookmark.setOnClickListener{
            notifyItemChanged(position)
            onFavoriteClick(user)
        }
    }

    class MyViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listUser: FavEntity) {
            binding.textViewName.text = listUser.username
            Glide.with(binding.root.context)
                .load(listUser.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.textViewUrl.text = listUser.htmlUrl
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_LOGIN, listUser.username)
                itemView.context.startActivity(intent)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavEntity>() {
            override fun areItemsTheSame(oldItem: FavEntity, newItem: FavEntity): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FavEntity, newItem: FavEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}