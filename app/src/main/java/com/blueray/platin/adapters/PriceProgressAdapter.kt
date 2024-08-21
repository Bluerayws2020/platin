package com.blueray.platin.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.R
import com.blueray.platin.databinding.ItemPriceProgressBinding


class PriceProgressAdapter(
    //private val productsList: List<ProductDetails>,
    private val onCategoryListener: onCategoryClick
) :
    RecyclerView.Adapter<PriceProgressAdapter.ProductsViewHolder>() {

    var listOfZeros = MutableList(100) { 0 }
    var selectedPosition = 20

    inner class ProductsViewHolder(val binding: ItemPriceProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemPriceProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 100
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.apply {


            if(position <= selectedPosition){
                setProgress(listOfZeros, selectedPosition)
            }

            if(listOfZeros[position] == 0){
                binding.dot.setBackgroundColor(itemView.context.resources.getColor(R.color.gray700))
            } else {
                binding.dot.setBackgroundColor(itemView.context.resources.getColor(R.color.orange_dark))
            }

            val params = binding.dot.layoutParams
            Log.e("*****", params.height.toString());
            if(position == 0 || position == (selectedPosition - 1)){
                val params = binding.dot.layoutParams
                params.height = 42
                params.width = 17
                binding.dot.layoutParams = params
            } else {
                val params = binding.dot.layoutParams
                params.height = 17
                params.width = 9
                binding.dot.layoutParams = params
            }


            binding.productCard.setOnClickListener {
                onCategoryListener.onItemClick(position = position , id = 0)
                selectedPosition = position
                setProgress(listOfZeros, position)
                notifyDataSetChanged()
            }

        }
    }


    fun setProgress(listOfZeros: MutableList<Int>, i: Int) {

        for (j in 0 .. 99) {
            listOfZeros[j] = 0
        }
        if (i <= listOfZeros.size) {
            for (j in 0 until i.coerceAtMost(i)) {
                listOfZeros[j] = 1
            }
        }
    }

}