package com.blueray.platin.API

interface CartItemListener {
    fun onRemoveClick(position: Int, id: String)
    fun onChangeQuantityClick(id: String , quantity:String)
}