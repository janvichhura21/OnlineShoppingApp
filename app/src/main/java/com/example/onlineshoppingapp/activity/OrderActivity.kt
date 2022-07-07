package com.example.onlineshoppingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.addFragment
import com.example.onlineshoppingapp.fragment.OrderDetailFragment

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        addFragment(R.id.order_frame,OrderDetailFragment())
    }
}