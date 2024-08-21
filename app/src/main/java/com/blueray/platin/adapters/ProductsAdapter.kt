package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.databinding.ItemProductBinding
import com.blueray.platin.models.Product
import com.bumptech.glide.Glide


class ProductsAdapter(
    private var list: MutableList<Product>,  // Change to MutableList
    private val onProductListener: OnProductListener
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(), Filterable {

    private var filteredProductsList: MutableList<Product> = list

    inner class ProductsViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredProductsList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val data = filteredProductsList[position]
        holder.apply {
            binding.productTitle.text = data.name
            Glide.with(itemView.context).load(data.image).into(binding.itemPic)

            binding.productCard.setOnClickListener {
                onProductListener.showDetails(data.id)
            }
        }
    }

    // Method to add more products to the list
    fun addProducts(newProducts: List<Product>) {
        val startPosition = list.size
        list.addAll(newProducts)
        filteredProductsList = list
        notifyItemRangeInserted(startPosition, newProducts.size)
    }

    // Method to get current list of products
    fun getProducts(): List<Product> {
        return list
    }
    fun clearProducts() {
        (list as MutableList).clear()
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchText = constraint?.toString()?.lowercase() ?: ""
                filteredProductsList = if (searchText.isEmpty()) {
                    list
                } else {
                    list.filter { product ->
                        product.name.lowercase().contains(searchText)
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = filteredProductsList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProductsList = results?.values as? MutableList<Product> ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }
}

