package com.jks.userrecognitionandaccesspermission.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.listener.allowaccessListener
import com.jks.userrecognitionandaccesspermission.listener.removeAccessListener
import com.jks.userrecognitionandaccesspermission.models.AllowsharingItem
import com.jks.userrecognitionandaccesspermission.models.FeedItem
import kotlinx.android.synthetic.main.each_row_admin_feed.view.*
import kotlinx.android.synthetic.main.each_row_admin_notification.view.*

class FeedAdapter(val context:Context, val list: List<FeedItem>): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {


    inner class FeedViewHolder(itemview:View) : RecyclerView.ViewHolder(itemview)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.each_row_admin_feed,parent,false)
      return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
     val feed = list[position]
        Glide.with(context).load(feed.postedBy.profilepic).placeholder(R.drawable.ic_baseline_add_24).into(holder.itemView.iv_feed_profilepic)
        Glide.with(context).load(feed.picture).placeholder(R.drawable.ic_baseline_add_24).into(holder.itemView.iv_feed_pic)
        holder.itemView.tv_feed_name.text=feed.postedBy.name
        holder.itemView.tv_feed_title.text=feed.title
        holder.itemView.date.text="posted on"+feed.updatedAt
        holder.itemView.tv_feed_desc.text=feed.description
    }

    override fun getItemCount(): Int {
      return list.size
    }
}