package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.R
import com.blueray.platin.databinding.ItemCompanyBinding
import com.blueray.platin.databinding.ItemPicturBinding
import com.blueray.platin.databinding.ItemProductBinding
import com.blueray.platin.databinding.ItemProductHorizontalBinding


class CompaniesAdapter(
    //private val productsList: List<ProductDetails>,
    private val onProductListener: onCategoryClick
) :
    RecyclerView.Adapter<CompaniesAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.apply {


            binding.productCard.setOnClickListener {
                onProductListener.onItemClick(position)
            }
            if(position != 0){
                binding.title.isVisible = false
                binding.describtion.isVisible = false
                binding.productCard.setStrokeColor(itemView.context.getResources().getColor(R.color.gray500))
            }


//            binding.favouriteClick.setOnCheckedChangeListener{ buttonView, isChecked ->
//                if (buttonView?.isPressed == true) {
//                    if (!HelperUtils.isGuest(buttonView.context)) {
//                        if (isChecked) {
//                            val pid = filteredProductsList [position].pid
//                            onProductListener.addToFavourite(pid)
//                            filteredProductsList [position].favorite = "1"
//                        } else {
//                            val favouriteId = filteredProductsList [position].pid
//                            onProductListener.removeFromFavourite(favouriteId)
//                            filteredProductsList [position].favorite = "0"
//                        }
//                    }
//                }
//            }
        }


    }

}