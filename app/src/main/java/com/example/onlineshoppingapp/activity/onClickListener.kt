package com.example.onlineshoppingapp.activity

import com.example.onlineshoppingapp.model.Cart

interface onClickListener {

    fun onclick(cart: Cart)
    fun onButtonClick(cart: Cart)
}