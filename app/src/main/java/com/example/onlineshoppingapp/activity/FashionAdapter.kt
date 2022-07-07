package com.example.onlineshoppingapp.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.databinding.FashionItemsBinding
import com.example.onlineshoppingapp.model.Fashion

class FashionAdapter(val context: Context):RecyclerView.Adapter<FashionAdapter.FashionViewHolder>() {

    var fashionList:List<Fashion> = listOf()

    set(value) {
        notifyItemRangeRemoved(1, fashionList.size)
        field = value
        notifyItemRangeInserted(1, fashionList.size)
    }
    class FashionViewHolder(val binding: FashionItemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Fashion){
            binding.value=data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FashionViewHolder {
        return FashionViewHolder(FashionItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FashionViewHolder, position: Int) {
        val data=fashionList[position]
        holder.bind(data)
        Glide.with(context)
            .load(data.url)
            .into(holder.binding.image)
    }

    override fun getItemCount(): Int {
       return fashionList.size
    }
}