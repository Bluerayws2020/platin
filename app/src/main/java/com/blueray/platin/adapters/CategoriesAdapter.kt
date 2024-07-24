package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.databinding.CategoryItemBinding


class CategoriesAdapter(val onCategoryClick: onCategoryClick) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.apply {

            binding.categoryCard.setOnClickListener{
                onCategoryClick.onItemClick(position)
            }

        }


    }
}