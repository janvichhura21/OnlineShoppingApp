package com.example.onlineshoppingapp.login

import android.net.Uri
import android.util.Log
import com.example.onlineshoppingapp.model.User
import com.example.onlineshoppingapp.network.BaseApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class LoginRepository:BaseApi() {
    val db= FirebaseFirestore.getInstance()
    fun savePersonalDetails(user:User){
        val data = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "password" to user.password,
             "id" to user.id,
            "profilePic" to user.id

        )
        db.collection("User")
            .document(FirebaseAuth.getInstance().uid!!)
            .set(data)
            .addOnSuccessListener {
                Log.d("success","successfully personal detail updated")
            }.addOnFailureListener {
                Log.d("error","unsuccessfully personal detail updated",it)
            }
    }

    fun saveImage(imageUri: Uri,user: User) = callbackFlow<String> {
        val updateMap: MutableMap<String, Any> = HashMap()
        updateMap["profilePic"] = user.profilePic
        uploadImage(
            getPathString("notes-pic", imageUri),
            imageUri,
            { url ->
                Log.d("janvi", "$url")
                db.collection("User")
                    .document(FirebaseAuth.getInstance().uid!!)
                    .set(url, SetOptions.merge())
            },
            {
                Log.d("erroe",it.message.toString())

            })
        awaitClose {}
    }

}