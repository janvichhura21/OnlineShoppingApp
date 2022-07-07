package com.example.onlineshoppingapp.activity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.databinding.CartItemsBinding
import com.example.onlineshoppingapp.model.Cart

class CartAdapter(val context: Context,
                  val onClickListener: onClickListener):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var qty=1
    var cartdetails:List<Cart> = listOf()

    set(value) {
        notifyItemRangeRemoved(1,cartdetails.size)
        field=value
        notifyItemRangeInserted(1,cartdetails.size)
    }
    class CartViewHolder(val binding: CartItemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Cart){
            binding.cartlist=data
            data.tPrice=data.price.toInt()
           binding.totalPrice.text= data.price

            binding.qty.text= 1.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val data=cartdetails[position]
        holder.bind(data)
        Glide.with(context)
            .load(data.url)
            .into(holder.binding.cartImage)
        holder.binding.addQty.setOnClickListener {
            qty+=1
            if (qty>=1) {
                var totalprice=holder.binding.totalPrice.text.toString().toInt()
                val price= data.price.toInt()
                totalprice+=price
                holder.binding.totalPrice.text=totalprice.toString()
                data.tPrice= holder.binding.totalPrice.text.toString().toInt()
                Log.d("tprice",data.tPrice.toString())
                holder.binding.qty.text = qty.toString()
                data.q=holder.binding.qty.text.toString().toInt()
                Log.d("totalPrice",totalprice.toString())
            }
        }
        holder.binding.removeQty.setOnClickListener {
            qty-=1
            if (qty>=1) {
                var totalprice=holder.binding.totalPrice.text.toString().toInt()
                val price= data.price.toInt()
                totalprice-=price
                holder.binding.totalPrice.text=totalprice.toString()
                data.tPrice= holder.binding.totalPrice.text.toString().toInt()
                holder.binding.qty.text = qty.toString()
                data.q=holder.binding.qty.text.toString().toInt()
                holder.binding.qty.text = qty.toString()
            }
        }
        holder.binding.deleteItem.setOnClickListener {
            onClickListener.onclick(data)
        }
        holder.binding.proceed.setOnClickListener {
            onClickListener.onButtonClick(data)
        }
    }

    override fun getItemCount(): Int {
       return cartdetails.size
    }
}