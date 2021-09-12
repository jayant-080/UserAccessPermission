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
import com.jks.userrecognitionandaccesspermission.models.RequestItem
import kotlinx.android.synthetic.main.each_row_admin_notification.view.*

class AdminNotificationAdapter(val context:Context,val list: List<RequestItem>,val alistener:allowaccessListener,val rlistener:removeAccessListener): RecyclerView.Adapter<AdminNotificationAdapter.AdminNotiViewholder>() {


    inner class AdminNotiViewholder(itemview:View) : RecyclerView.ViewHolder(itemview){
   init {

       itemview.iv_admin_noti_allowaccess.setOnClickListener {
            alistener.allowAccessListener(adapterPosition,itemview)
       }
       itemview.iv_admin_noti_removeaccess.setOnClickListener {
          rlistener.removeAccessListener(adapterPosition,itemview)
       }
   }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminNotiViewholder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.each_row_admin_notification,parent,false)
      return AdminNotiViewholder(view)
    }

    override fun onBindViewHolder(holder: AdminNotiViewholder, position: Int) {
     val user = list[position]
        Glide.with(context).load(user.profilepic).into(holder.itemView.iv_adminnoti_profile)
        holder.itemView.tv_admin_noti_name.text= user.name
        holder.itemView.tv_admin_noti_email.text=user.email
    }

    override fun getItemCount(): Int {
      return list.size
    }
}