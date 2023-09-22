package com.example.submissionone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionone.R
import com.example.submissionone.data.response.UserResponse
import de.hdodenhof.circleimageview.CircleImageView

class SelectAdapter(val dataUser: List<UserResponse>) :
    RecyclerView.Adapter<SelectAdapter.MyViewHolder>(){
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imgAvatar = view.findViewById<CircleImageView>(R.id.img_item_photo)
            val loginUsername = view.findViewById<TextView>(R.id.text_View_Name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return dataUser.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.loginUsername.text = dataUser.get(position).login

            Glide.with(holder.imgAvatar)
                .load(dataUser[position].avatarUrl)
                .into(holder.imgAvatar)

            holder.itemView.setOnClickListener {
                onClick?.invoke(dataUser[position])
            }
        }

        var onClick: ((UserResponse) -> Unit)? = null


    }