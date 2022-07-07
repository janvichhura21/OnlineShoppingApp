package com.example.onlineshoppingapp.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshoppingapp.MainActivity
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.databinding.ActivityProfileBinding
import com.example.onlineshoppingapp.model.User


class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel:LoginViewModel
    var uri: Uri? = null
    companion object{
        val IMAGE_REQUEST_CODE=100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_profile)
        binding.root
        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        setListeners()
        setBtn()
    }

    private fun setBtn() {
        binding.saveBtn.setOnClickListener {
            viewModel.result.observe(this, Observer {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            })
        }
    }

    fun setListeners(){
        binding.edtProfilePic.setOnClickListener {
            if (binding.edtProfilePic.isEnabled) {
                picImage()
            }
        }
    }
    private fun picImage() {
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== IMAGE_REQUEST_CODE&&resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data?.data!!
            binding.edtProfilePic.setImageURI(uri)
            viewModel.uploadImage(this,uri!!)
            //viewModel.saveImageUrl(uri!!)
            //   binding.progressLayout.visibility= View.VISIBLE
        }
    }

}