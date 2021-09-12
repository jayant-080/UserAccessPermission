package com.jks.userrecognitionandaccesspermission.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.listener.SentRequestListener
import com.jks.userrecognitionandaccesspermission.listener.seefeedlistener
import com.jks.userrecognitionandaccesspermission.models.UserItem
import kotlinx.android.synthetic.main.each_row_alladmin_list.view.*


class ListOfAdminAdapter(val context: Context, val list:List<UserItem>,val listener:SentRequestListener,val currentUid:String,val seefeedlistener: seefeedlistener) : RecyclerView.Adapter<ListOfAdminAdapter.ListOfAdminViewHolder>() {

    inner class ListOfAdminViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){

        init {
            itemview.button_send_request.setOnClickListener {
                listener.sendRequestListener(adapterPosition,itemview)
            }

            itemview.button_view_feed.setOnClickListener {
                seefeedlistener.seeFeedListener(adapterPosition,itemview)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfAdminViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_row_alladmin_list,parent,false)
        return ListOfAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListOfAdminViewHolder, position: Int) {

        val user = list[position]
           Glide.with(context).load(user.profilepic).into(holder.itemView.iv_ladminniti_profile)
            holder.itemView.tv_listadmin_name.text=user.name
            holder.itemView.tv_listadmin_email.text=user.email
          if(user.isadmin){
              holder.itemView.tv_listadmin_admin.text= "admin"
              holder.itemView.tv_listadmin_admin.setTypeface(Typeface.DEFAULT_BOLD)
          }else{
              holder.itemView.tv_listadmin_admin.text= "user"
              holder.itemView.button_send_request.isVisible=false
          }

        if(user.request!!.contains(currentUid)){
            holder.itemView.button_send_request.isVisible=false
            holder.itemView.button_send_request_green.isVisible=true
        }else if(user.allowsharing!!.contains(currentUid)){
            holder.itemView.button_send_request.isVisible=false
            holder.itemView.button_view_feed.isVisible=true

        }




    }

    override fun getItemCount(): Int {
        return list.size
    }
}