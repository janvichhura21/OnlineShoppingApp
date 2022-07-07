package com.example.onlineshoppingapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.databinding.FragmentAddToBinding
import com.example.onlineshoppingapp.model.Cart
import com.example.onlineshoppingapp.viewmodel.HomeViewModel

class AddToFragment : Fragment(),onClickListener{
    lateinit var binding: FragmentAddToBinding
    private val viewModel:HomeViewModel by viewModels()
    lateinit var cartAdapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_add_to, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartAdapter= CartAdapter(requireContext(),this)
        viewModel.getCartDetails()
        setupMv()
        setup()


    }

    private fun setup() {
        viewModel.cartDetails.observe(viewLifecycleOwner, Observer {
            cartAdapter.cartdetails=it
        })
    }

    private fun setupMv() {
        binding.addToCartRv.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=cartAdapter
        }
    }

    override fun onclick(cart: Cart) {
        Toast.makeText(requireContext(), "delete items", Toast.LENGTH_SHORT).show()
        viewModel.deleteDetails()
    }

    override fun onButtonClick(cart: Cart) {
        Log.d("total", cart.tPrice.toString())
        Toast.makeText(requireContext(), "cart items updated", Toast.LENGTH_SHORT).show()
        val intent=Intent(requireActivity(),OrderActivity::class.java)
        intent.putExtra("name",cart.name)
        intent.putExtra("price",cart.price)
        intent.putExtra("tPrice",cart.tPrice)
        intent.putExtra("url",cart.url)
        intent.putExtra("q",cart.q)
        viewModel.updateCart(cart)
        startActivity(intent)
    }
}