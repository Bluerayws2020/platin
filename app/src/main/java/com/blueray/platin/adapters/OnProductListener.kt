package com.blueray.platin.adapters

interface OnProductListener {
    fun addToCart(pid: Int, quantity: String)
    fun addToFavourite(pid: Int)
    fun removeFromFavourite(favId: Int)
    fun showDetails(pid: Int)
}