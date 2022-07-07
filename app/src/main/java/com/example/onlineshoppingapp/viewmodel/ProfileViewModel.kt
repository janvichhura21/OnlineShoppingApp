package com.example.onlineshoppingapp.viewmodel

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshoppingapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileViewModel:ViewModel() {
    val db= FirebaseFirestore.getInstance()
    val image=MutableLiveData<String>()
    var edtname=""
    val data=MutableLiveData<String>()
    val email=MutableLiveData<String>()
    val result=MutableLiveData<User>()
    fun getDetails(){
        if (FirebaseAuth.getInstance().uid!=null){
            db.collection("User")
                .document(FirebaseAuth.getInstance().uid!!)
                .get()
                .addOnSuccessListener {
                    val data=it.toObject(User::class.java)
                    result.value=data
                    edtname=data!!.name
                }.addOnFailureListener {
                    Log.d("error",it.message.toString())
                }
        }
    }
    fun savePersonalDetails(user:User){
        val data = hashMapOf(
            "name" to user.name,
            "email" to user.email,
         //   "password" to user.password,
            "id" to user.id,
            "profilePic" to user.profilePic

        )
        db.collection("User")
            .document(FirebaseAuth.getInstance().uid!!)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("success","successfully personal detail updated")
            }.addOnFailureListener {
                Log.d("error","unsuccessfully personal detail updated",it)
            }
    }
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mProgressDialog: ProgressDialog
    fun uploadImage(context: Context, imageFileUri: Uri) {
        mProgressDialog = ProgressDialog(context)
        mProgressDialog.setMessage("Please wait, image being upload")
        mProgressDialog.show()
        val date = Date()
        val uploadTask = mStorageRef.child("posts/${date}.png").putFile(imageFileUri)
        uploadTask.addOnSuccessListener {
            Log.e("Frebase", "Image Upload success")
            mProgressDialog.dismiss()
            val uploadedURL = mStorageRef.child("posts/${date}.png").downloadUrl
            uploadedURL.addOnSuccessListener { url->
                data.value= url.toString()
                updateImage(url)
                Log.e("Firebase", "Uploaded $url")
            }
            Log.e("Firebase", "Uploaded $uploadedURL")
        }.addOnFailureListener {
            Log.e("Frebase", "Image Upload fail")
            mProgressDialog.dismiss()
        }
    }

    private fun updateImage(url: Uri) {
        val user=User()
        user.profilePic= url.toString()
        val updateMap: MutableMap<String, Any> = HashMap()
        updateMap["profilePic"] = user.profilePic
        val db = FirebaseFirestore.getInstance()
        db.collection("User")
            .document(FirebaseAuth.getInstance().uid!!)
            .set(updateMap, SetOptions.merge())

    }


}