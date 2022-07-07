package com.example.onlineshoppingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onlineshoppingapp.activity.HomeAdapter
import com.example.onlineshoppingapp.databinding.FragmentHomeBinding
import com.example.onlineshoppingapp.model.Shopping
import com.example.onlineshoppingapp.viewmodel.HomeViewModel
import java.util.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeAdapter: HomeAdapter
    private val viewModel:HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        homeAdapter= HomeAdapter(requireContext())
        viewModel.getData()
        setup()
        setupMv()

    }


    private fun setupMv() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            homeAdapter.listitem=it
        })
    }

    private fun setup() {
        binding.rv.apply {
            layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter=homeAdapter
        }
    }
}