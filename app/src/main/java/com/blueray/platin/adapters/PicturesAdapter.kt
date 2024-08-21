package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.databinding.ItemPicturBinding


class PicturesAdapter(
    //private val productsList: List<ProductDetails>,
    private val onProductListener: onCategoryClick
) :
    RecyclerView.Adapter<PicturesAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(val binding: ItemPicturBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemPicturBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.apply {


            binding.productCard.setOnClickListener {
                onProductListener.onItemClick(position = position , id = 0)
            }

        }


    }

}