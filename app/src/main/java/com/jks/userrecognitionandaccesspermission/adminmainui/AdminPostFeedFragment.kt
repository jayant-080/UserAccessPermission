package com.jks.userrecognitionandaccesspermission.adminmainui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.api.UploadfeedApi
import com.jks.userrecognitionandaccesspermission.models.UploadFeedResponce
import com.jks.userrecognitionandaccesspermission.utils.UploadRequestBody
import com.jks.userrecognitionandaccesspermission.utils.getFileName
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import kotlinx.android.synthetic.main.fragment_admin_post_feed.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AdminPostFeedFragment:Fragment(R.layout.fragment_admin_post_feed), UploadRequestBody.UploadCallback {
    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
        val token = prefs.getString("token",null)!!
        iv_admin_post_image.setOnClickListener {

            openImageChooser()

        }
        iv_choose_post_photo.setOnClickListener {

            openImageChooser()

        }

        ivclosebtn.setOnClickListener {
            requireActivity().finish()
        }

        ivuploadpost.setOnClickListener {

            val title = et_admin_feed_title.text.toString()
            val body = et_admin_feed_desc.text.toString()
                uploadImage(token,title,body)

        }
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    imageuri = data?.data
                    iv_admin_post_image.setImageURI(imageuri)
                }
            }
        }
    }

    private fun uploadImage(token:String,title:String,description:String) {
        if (imageuri == null) {
            root_post_feed.snackbar("Select an Image First")
            return
        }

        val parcelFileDescriptor = requireActivity().contentResolver.openFileDescriptor(imageuri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(imageuri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        pb_feed_upload.progress = 0
        val body = UploadRequestBody(file, "image", this)

        lifecycleScope.launchWhenStarted {
            UploadfeedApi().postFeed(
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), title),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(),description),
                MultipartBody.Part.createFormData(
                    "picture",
                    file.name,
                    body
                ),
                token).enqueue(object : retrofit2.Callback<UploadFeedResponce>{
                override fun onResponse(call: Call<UploadFeedResponce>, response: Response<UploadFeedResponce>) {
                    pb_feed_upload.isVisible=true
                    pb_feed_upload.progress=100
                    root_post_feed.snackbar(response.body()!!.message)
                    pb_feed_upload.isVisible=false
                }

                override fun onFailure(call: Call<UploadFeedResponce>, t: Throwable) {
                    root_post_feed.snackbar(t.message.toString())
                    pb_feed_upload.progress = 0
                    pb_feed_upload.isVisible=false
                }

            })
        }


    }

    private  var imageuri: Uri?= null
    override fun onProgressUpdate(percentage: Int) {
        pb_feed_upload.progress=percentage
    }
}