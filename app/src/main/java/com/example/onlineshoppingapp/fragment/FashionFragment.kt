package com.example.onlineshoppingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.activity.AddToFragment
import com.example.onlineshoppingapp.activity.FashionAdapter
import com.example.onlineshoppingapp.databinding.FragmentFashionBinding
import com.example.onlineshoppingapp.model.Cart
import com.example.onlineshoppingapp.replaceFragment
import com.example.onlineshoppingapp.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class FashionFragment : Fragment() {
    lateinit var binding: FragmentFashionBinding
    private lateinit var fashionAdapter: FashionAdapter
    private val viewModel:HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_fashion, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fashionAdapter= FashionAdapter(requireContext())
        viewModel.getFashion()
      setup()
        addCartBtn()
    }

    private fun addCartBtn() {

    }

    private fun setup() {
        val imageUrl=activity?.intent!!.getStringExtra("url")
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.image)
        var cartname=activity?.intent!!.getStringExtra("name")
        binding.title.setText(cartname)
        val cartprice=activity?.intent!!.getStringExtra("price")
        binding.price.setText(cartprice)
        binding.addCartBtn.setOnClickListener {
            val cart=Cart().apply {
                id=FirebaseAuth.getInstance().uid!!
                name=cartname!!
                url=imageUrl!!
                price=cartprice!!
            }
            viewModel.addCart(cart)
            activity?.replaceFragment(R.id.frame,AddToFragment())
        }
    }


}