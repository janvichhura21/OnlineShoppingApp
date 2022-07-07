package com.example.onlineshoppingapp.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.databinding.ItemsBinding
import com.example.onlineshoppingapp.model.Shopping

class HomeAdapter(val context: Context):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var listitem: List<Shopping> = listOf()

    set(value) {
        notifyItemRangeRemoved(1, listitem.size)
        field = value
        notifyItemRangeInserted(1, listitem.size)
    }
    class HomeViewHolder(val binding: ItemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Shopping){
            binding.lisitems=data
        }
    }
    private val differCallBack = object : DiffUtil.ItemCallback<Shopping>(){
        override fun areItemsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
            return oldItem == newItem
        }

    }
    val differ= AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data=listitem[position]
        Glide.with(context)
            .load(data.url)
            .into(holder.binding.imageView)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            val intent=Intent(context,DetailActivity::class.java)
            intent.putExtra("url",data.url)
            intent.putExtra("name",data.name)
            intent.putExtra("price",data.price)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return listitem.size
    }
}