package com.example.onlineshoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshoppingapp.model.Cart
import com.example.onlineshoppingapp.model.Fashion
import com.example.onlineshoppingapp.model.Shopping
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class HomeViewModel:ViewModel() {
    val result=MutableLiveData<List<Shopping>>()
    val resultData=MutableLiveData<List<Fashion>>()
    val cartDetails=MutableLiveData<List<Cart>>()
    val repository=AddCartRepository()
    val db=FirebaseFirestore.getInstance()
    fun getData(){
        db.collection("Shopping")
            .get()
            .addOnSuccessListener {
                val data=it.toObjects(Shopping::class.java)
                result.value=data
                Log.d("viewModel",data.toString())
            }.addOnFailureListener {
                Log.d("viewModel",it.message.toString())
            }
    }
    fun getFashion(){
        db.collection("Fashion")
            .get()
            .addOnSuccessListener {
                val data=it.toObjects(Fashion::class.java)
                resultData.value=data
                Log.d("viewModel",data.toString())
            }.addOnFailureListener {
                Log.d("viewModel",it.message.toString())
            }
    }

    fun getDetailFashion(){
        db.collection("Fashion")
            .get()
            .addOnSuccessListener {
                val data=it.toObjects(Fashion::class.java)
                resultData.value=data
                Log.d("viewModel",data.toString())
            }.addOnFailureListener {
                Log.d("viewModel",it.message.toString())
            }
    }

    fun addCart(cart: Cart){
        repository.saveCart(cart)
    }
    fun updateCart(cart: Cart){
        val data = hashMapOf(
            "tPrice" to cart.tPrice,
            "q" to cart.q
        )
        db.collection("Cart")
            .whereEqualTo("id",FirebaseAuth.getInstance().uid)
            .get()
            .addOnSuccessListener {
                for (document in it){
                    db.collection("Cart")
                        .document(document.id)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("success","successfully updates")
                        }.addOnFailureListener {
                            Log.d("unsuccess","Failed updates")
                        }
                }
            }
    }

    fun getCartDetails(){
        db.collection("Cart")
            .whereEqualTo("id",FirebaseAuth.getInstance().uid!!)
            .get()
            .addOnSuccessListener {
                val data=it.toObjects(Cart::class.java)
                cartDetails.value=data
            }
    }

    fun deleteDetails(){
        val query=db.collection("Cart")
            .whereEqualTo("id",FirebaseAuth.getInstance().uid!!)
            .get()
        query.addOnSuccessListener {
            for (document in it){
                db.collection("Cart").document(document.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("success","successfully  deleted")
                    }.addOnFailureListener {
                        Log.d("unSuccess","successfully  deleted")
                    }
            }
        }
    }

}