package com.example.onlineshoppingapp.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.databinding.FragmentLoginBinding
import com.example.onlineshoppingapp.replaceFragment

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel:LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        goToSignUp()
        getLogin()
    }

    private fun getLogin() {
        binding.button.setOnClickListener {
            val edtEmail = binding.edtEmail.text.toString()
            val edtPassword = binding.edtPassword.text.toString()
            if (edtEmail.isNotEmpty() && edtPassword.isNotEmpty()) {
                viewModel.alreadyLogin(edtEmail, edtPassword)
            } else {
                if (edtEmail.isEmpty()) {
                    binding.layoutEmail.error = "please Enter the email"

                } else if (edtPassword.isEmpty()) {
                    binding.layoutPassword.error = "please Enter the password"
                }
            }
        }
    }

    private fun goToSignUp() {
        binding.gotToSignUp.setOnClickListener {
            activity?.replaceFragment(R.id.frame,SignUpFragment())
        }
    }

}