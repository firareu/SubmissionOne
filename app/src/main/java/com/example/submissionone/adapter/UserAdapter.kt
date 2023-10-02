package com.example.submissionone.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionone.data.response.UserResponse
import com.example.submissionone.databinding.ItemUserBinding
import com.example.submissionone.local.entity.FavEntity

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val userList = ArrayList<UserResponse>()
    //private val favList = ArrayList<FavEntity>()
    //private var onBookmarkClick: ((FavEntity) -> Unit)? = null

    /*fun setFavList(favs: List<FavEntity>) { //private var onBookmarkClick: ((FavEntity) -> Unit)?= null
        favList.clear()
        favList.addAll(favs)
    }*/


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

        /*val ivBookmark = holder.binding.ivBookmark
        val fav = favList.find { it.id == user.id }
        if (fav != null) {
            if (fav.isBookmarked) {
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_favorite_24_white))
            } else {
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_favorite_border_24_white))
            }
        }
        ivBookmark.setOnClickListener {
            if (fav != null) {
                onBookmarkClick?.invoke(fav)
                holder.bind2(fav)
            }
        }*/

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
        /*fun bind2(fav: FavEntity) {
            binding.textViewName.text = fav.login
            Glide.with(itemView.context)
                .load(fav.avatarUrl)
                //.apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgItemPhoto)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(fav.htmlUrl)
                itemView.context.startActivity(intent)
            }
        }*/
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    /*fun setOnBookmarkClickCallback(onBookmarkClick: (FavEntity) -> Unit) {
        this.onBookmarkClick = onBookmarkClick
    }*/

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }
}