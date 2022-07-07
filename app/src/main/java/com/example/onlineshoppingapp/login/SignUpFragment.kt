package com.example.onlineshoppingapp.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshoppingapp.MainActivity
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.databinding.FragmentSignUpBinding
import com.example.onlineshoppingapp.replaceFragment
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel:LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        checkUserLogin()
        goToLogin()
        btnCLick()
    }

    private fun checkUserLogin() {
        if (FirebaseAuth.getInstance().currentUser!=null){
            activity?.startActivity(Intent(requireActivity(), MainActivity::class.java))
            activity?.finish()
        }
    }

    private fun btnCLick() {
        binding.button.setOnClickListener {
            val edtName=binding.name.text.toString()
            val edtEmail=binding.email.text.toString()
            val edtPassword=binding.password.text.toString()
            if (edtName.isNotEmpty()&& edtEmail.isNotEmpty() && edtPassword.isNotEmpty()) {
                viewModel.getLogin(edtName, edtEmail, edtPassword)
                startActivity(Intent(requireActivity(),ProfileActivity::class.java))
                activity?.finish()
           }
           else{
                if (edtName.isEmpty()){
                    binding.layoutName.error="please Enter the name"
                }
               else if (edtEmail.isEmpty()){
                    binding.layoutEmail.error="please Enter the email"
                }
               else if (edtPassword.isEmpty()){
                    binding.layoutPassword.error="please Enter the password"
                }

            }
        }
    }
    private fun goToLogin() {
        binding.goToLogin.setOnClickListener {
            activity?.replaceFragment(R.id.frame,LoginFragment())
        }
    }
}