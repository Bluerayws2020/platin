package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.databinding.SubCategoryItemBinding
import com.blueray.platin.models.SubCategoriesData
import com.bumptech.glide.Glide


class SubCategoriesAdapter(
    val list: List<SubCategoriesData>, val onCategoryClick: onCategoryClick
) :
    RecyclerView.Adapter<SubCategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: SubCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            SubCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val data = list[position]
        holder.apply {

            binding.categoryName.text = data.name
            Glide.with(itemView.context).load(data.image).into(binding.categoryImage)

            binding.categoryCard.setOnClickListener {
                onCategoryClick.onItemClick(id = data.id, position = position)
            }

        }


    }
}