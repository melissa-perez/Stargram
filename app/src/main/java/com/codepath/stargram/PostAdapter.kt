package com.codepath.stargram

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView
        val ivImage: ImageView
        val tvDescription: TextView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            ivImage = itemView.findViewById(R.id.ivImage)
            tvDescription = itemView.findViewById(R.id.tvDescription)
        }

        fun bind(post: Post){
            tvDescription.text = post.getDescription()
            tvUserName.text = post.getUser()?.username

            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)

        }

    }
}