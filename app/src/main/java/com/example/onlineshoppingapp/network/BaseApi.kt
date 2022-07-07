package com.example.onlineshoppingapp.network

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

open class BaseApi {
    protected fun uploadImage(
        pathString: String,
        imageUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference
        val profilePicStorageRef =
            storageReference.child(pathString)
        val uploadTask = profilePicStorageRef.putFile(imageUri)
        uploadTask.addOnFailureListener {
            Log.d("error", "${it.message}")
            onFailure(it)
        }.addOnSuccessListener {
            Log.d("success", "${it.metadata}")
            val downloadUrl = profilePicStorageRef.downloadUrl
            downloadUrl.addOnSuccessListener { imageUrl ->
                Log.d("Success", imageUrl.toString())
                onSuccess(imageUrl.toString())
            }
        }
    }

    protected fun getPathString(folderName: String, imageUri: Uri) =
        "${folderName}/${FirebaseAuth.getInstance().uid}/${imageUri.lastPathSegment}"
}