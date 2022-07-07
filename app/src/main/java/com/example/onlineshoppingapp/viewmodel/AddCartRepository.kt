package com.example.onlineshoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.onlineshoppingapp.model.Cart
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AddCartRepository: ViewModel() {

    val db= FirebaseFirestore.getInstance()
    fun saveCart(cart: Cart){
        val data = hashMapOf(
            "name" to cart.name,
            "url" to cart.url,
            "price" to cart.price,
            "id" to cart.id,
            "tPrice" to cart.tPrice,
            "q" to cart.q
        )
        db.collection("Cart")
            .document()
            .set(data)
            .addOnSuccessListener {
                Log.d("success","successfully personal detail updated")
            }.addOnFailureListener {
                Log.d("error","unsuccessfully personal detail updated",it)
            }
    }

    fun updateCart(cart: Cart){
        val data = hashMapOf(
            "tPrice" to cart.tPrice,
            "q" to cart.q
        )
        db.collection("Cart")
            .document()
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("success","successfully personal detail updated")
            }.addOnFailureListener {
                Log.d("error","unsuccessfully personal detail updated",it)
            }
    }

}