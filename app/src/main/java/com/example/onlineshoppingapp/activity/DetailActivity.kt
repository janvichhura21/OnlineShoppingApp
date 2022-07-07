package com.example.onlineshoppingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.addFragment
import com.example.onlineshoppingapp.fragment.FashionFragment

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        addFragment(R.id.frame,FashionFragment())
    }
}