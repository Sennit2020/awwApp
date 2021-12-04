package com.example.awwapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.awwapp.R
import com.example.awwapp.model.Children
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_list_items.view.*

class RedditPostAdapter(private val childrenList: List<Children>) :
    RecyclerView.Adapter<RedditPostAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_list_items, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.author.text = childrenList[position].data.author
        holder.postTitle.text = childrenList[position].data.title
        holder.postedBy.text = childrenList[position].data.thumbnail
        if (childrenList[position].data.isVideo) {
            holder.viewVideo.visibility = View.VISIBLE
        }

        Picasso.get().load(childrenList[position].data.thumbnail).into(holder.postImage)

    }

    override fun getItemCount(): Int {
        return childrenList.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.author_text
        val postedBy: TextView = itemView.posted_by_text
        val postTitle: TextView = itemView.post_title
        val viewVideo: ImageView = itemView.play_button
        val postImage: ImageView = itemView.post_image
    }
}