package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.R
import com.blueray.platin.databinding.ItemCompanyBinding
import com.blueray.platin.models.GetOurCompaniesData
import com.bumptech.glide.Glide

class CompaniesAdapter(
    private val list: List<GetOurCompaniesData>
) : RecyclerView.Adapter<CompaniesAdapter.ProductsViewHolder>() {

    private var expandedPosition = -1 // Initially, no item is expanded

    // ViewHolder class
    inner class ProductsViewHolder(val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.apply {
            // Set the name, image, and description
            binding.title.text = list[position].name
            Glide.with(itemView.context).load(list[position].image).into(binding.itemPic)
            binding.describtion.text = list[position].description

            // Show description if this item is expanded
            binding.describtion.isVisible = position == expandedPosition

            // Set the stroke color based on whether the item is expanded
            if (position == expandedPosition) {
                binding.productCard.strokeColor = ContextCompat.getColor(itemView.context, R.color.blue) // Set stroke color to blue for selected item
            } else {
                binding.productCard.strokeColor = ContextCompat.getColor(itemView.context, R.color.gray500) // Set stroke color to gray for unselected items
            }

            // Click listener to toggle description visibility
            binding.productCard.setOnClickListener {
                val previousExpandedPosition = expandedPosition
                if (expandedPosition == position) {
                    // If the clicked item is already expanded, collapse it
                    expandedPosition = -1
                } else {
                    // Expand the clicked item and collapse the previously expanded one
                    expandedPosition = position
                    notifyItemChanged(previousExpandedPosition) // Notify the change to update the previous expanded item
                }
                notifyItemChanged(position) // Notify the change to update the current clicked item
            }
        }
    }
}
