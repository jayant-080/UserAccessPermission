package com.jks.userrecognitionandaccesspermission.sharedUi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jks.userrecognitionandaccesspermission.R
import com.jks.userrecognitionandaccesspermission.api.UploadfeedApi
import com.jks.userrecognitionandaccesspermission.models.UpdateProfilePicResponce
import com.jks.userrecognitionandaccesspermission.models.UploadFeedResponce
import com.jks.userrecognitionandaccesspermission.utils.UpdateProfileApiState
import com.jks.userrecognitionandaccesspermission.utils.UploadRequestBody
import com.jks.userrecognitionandaccesspermission.utils.getFileName
import com.jks.userrecognitionandaccesspermission.utils.snackbar
import com.jks.userrecognitionandaccesspermission.viewmodels.UpdateProfilePicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_post_feed.*
import kotlinx.android.synthetic.main.fragment_update_profile_pic.*
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@AndroidEntryPoint
class UpdateProfilePic:Fragment(R.layout.fragment_update_profile_pic), UploadRequestBody.UploadCallback {
    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
    var userid:String?=null
    var token:String?=null
    val updateProfilePicViewModel:UpdateProfilePicViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("logindetails",
            Context.MODE_PRIVATE
        )
        token = prefs.getString("token",null)!!
         userid = prefs.getString("id",null)!!
        iv_updatepic.setOnClickListener {

            openImageChooser()

        }
        iv_plus_updatepic.setOnClickListener {

            openImageChooser()

        }



        button_updatepic.setOnClickListener {
            uploadImage()
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
                    iv_updatepic.setImageURI(imageuri)
                }
            }
        }
    }

    private fun uploadImage() {
        if (imageuri == null) {
            root_updatepic.snackbar("Select an Image First")
            return
        }

        val parcelFileDescriptor = requireActivity().contentResolver.openFileDescriptor(imageuri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(imageuri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        pb_updatepic.progress = 0
        val body = UploadRequestBody(file, "image", this)
        val image = MultipartBody.Part.createFormData(
            "picture",
            file.name,
            body
        )
        updateProfilePicViewModel.updatePic(userid!!,image,token!!)
        lifecycleScope.launchWhenStarted {
            updateProfilePicViewModel.updateprofilestate.collect {
                  when(it){

                      is UpdateProfileApiState.Loading->{
                          pb_updatepic.isVisible=true
                         pb_updatepic.progress=100

                      }
                      is UpdateProfileApiState.Failure->{
                          pb_updatepic.isVisible=false
                          root_updatepic.snackbar(it.mssg.toString())
                          Log.d("lol",it.mssg.toString())
                      }
                      is UpdateProfileApiState.Success->{
                          pb_updatepic.isVisible=false
                          root_updatepic.snackbar(it.responce.message)
                      }
                  }
            }
        }



    }

    private  var imageuri: Uri?= null
    override fun onProgressUpdate(percentage: Int) {
        pb_updatepic.progress=percentage
    }
}