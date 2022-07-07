package com.example.onlineshoppingapp.login

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

class LoginViewModel:ViewModel() {
    private val firebaseAuth=FirebaseAuth.getInstance()
    private val repository=LoginRepository()
    val result=MutableLiveData<String>()
    fun getLogin(name:String,email:String,password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user=User(firebaseAuth.uid!!,name, email, password)
                repository.savePersonalDetails(user)
                Log.d("success","successfully sign Up")
            }.addOnFailureListener {
                Log.d("unsuccess","Failed to sign Up")
            }
    }

    fun alreadyLogin(email:String,password:String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("success","successfully login")
            }.addOnFailureListener {
                Log.d("unsuccess","Failed to login")
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
                result.value= url.toString()
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