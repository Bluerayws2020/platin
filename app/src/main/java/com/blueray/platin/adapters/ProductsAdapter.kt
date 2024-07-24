package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.databinding.ItemProductBinding


class ProductsAdapter(
    //private val productsList: List<ProductDetails>,
    private val onProductListener: OnProductListener
) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() , Filterable {

    private var filteredProductsList: List<String> = emptyList()

    inner class ProductsViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 16
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.apply {


            binding.productCard.setOnClickListener {
                onProductListener.showDetails(position)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchText = constraint?.toString()?.toLowerCase() ?: ""
//                filteredProductsList = if (searchText.isEmpty()) {
//                    productsList
//                } else {
//                    productsList.filter { product ->
//                        if(product.category_name != null && !isHome)
//                            product.title.toLowerCase().contains(searchText) || product.category_name.toLowerCase().contains(searchText)
//                        else
//                            product.title.toLowerCase().contains(searchText)
//                    }
//                }
                val filterResults = FilterResults()
                filterResults.values = filteredProductsList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProductsList = results?.values as? List<String> ?: emptyList()
               // Log.e("***" , filteredProductsList.map { it.title }.toString())
//                Log.e("***" , filteredProductsList.map { it.category_name }.toString())
                notifyDataSetChanged()
            }
        }
    }
}