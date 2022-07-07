package com.example.onlineshoppingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.activity.EditActivity
import com.example.onlineshoppingapp.databinding.ActivityProfileBinding
import com.example.onlineshoppingapp.databinding.FragmentProfileBinding
import com.example.onlineshoppingapp.login.SignUpActivity
import com.example.onlineshoppingapp.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
  lateinit var binding: FragmentProfileBinding
  private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        binding.value=viewModel
        viewModel.getDetails()
        binding.progressBar.isVisible=true
        viewModel.result.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext())
                .load(it.profilePic)
                .into(binding.profilePic)
            binding.progressBar.isVisible=false
            binding.text.text=it.name
            Log.d("jaanvi", it.toString())
        })
        clickbtn()
        binding.naviView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout-> signout()
            }
            true
        }
    }

    private fun signout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(requireActivity(),SignUpActivity::class.java))
        activity?.finish()
    }

    private fun clickbtn() {
        binding.edtbtn.setOnClickListener {
            val intent=Intent(requireContext(),EditActivity::class.java)
            viewModel.result.observe(viewLifecycleOwner, Observer {
                intent.putExtra("name",it.name)
                intent.putExtra("email",it.email)
                intent.putExtra("url",it.profilePic)
                startActivity(intent)
            })
        }
    }
}