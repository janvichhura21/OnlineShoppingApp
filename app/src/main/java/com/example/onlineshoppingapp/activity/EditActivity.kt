package com.example.onlineshoppingapp.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.MainActivity
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.databinding.ActivityEditBinding
import com.example.onlineshoppingapp.login.ProfileActivity
import com.example.onlineshoppingapp.model.User
import com.example.onlineshoppingapp.replaceFragment
import com.example.onlineshoppingapp.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
  private val viewModel: ProfileViewModel by viewModels()
    var uri: Uri? = null
    companion object{
        val IMAGE_REQUEST_CODE=100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_edit)
       binding.root
        getallobject()
        setListeners()
        setData()
    }

    private fun setData() {
        binding.saveEdtBtn.setOnClickListener {
            val user = User()
                .apply {
                    id = FirebaseAuth.getInstance().uid!!
                    name = binding.edtName.text.toString()
                    email = binding.edtEmail.text.toString()
                }
            viewModel.data.observe(this, Observer {
                user.profilePic=it
                Glide.with(this)
                    .load(user.profilePic)
                    .into(binding.editImage)
                viewModel.savePersonalDetails(user)
            })
            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }
    }

    private fun getallobject() {
        val name=intent.getStringExtra("name")
        val email=intent.getStringExtra("email")
        val url=intent.getStringExtra("url")
        binding.edtName.setText(name)
        binding.edtEmail.setText(email)
        Glide.with(this)
            .load(url)
            .into(binding.editImage)
    }
    fun setListeners(){
        binding.editImage.setOnClickListener {
            if (binding.editImage.isEnabled) {
                picImage()
            }
        }
    }
    private fun picImage() {
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, ProfileActivity.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== ProfileActivity.IMAGE_REQUEST_CODE &&resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data?.data!!
            binding.editImage.setImageURI(uri)
            viewModel.uploadImage(this,uri!!)
        }
    }
}