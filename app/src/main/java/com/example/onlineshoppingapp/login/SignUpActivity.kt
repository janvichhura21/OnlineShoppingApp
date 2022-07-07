package com.example.onlineshoppingapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.addFragment

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        addFragment(R.id.frame,SignUpFragment())
    }
}