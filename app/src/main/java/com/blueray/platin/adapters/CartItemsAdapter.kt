package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.API.CartItemListener
import com.blueray.platin.R
import com.blueray.platin.databinding.ItemCartHorizontalBinding
import com.blueray.platin.models.CustomerCart
import com.bumptech.glide.Glide


class CartItemsAdapter(
    var list: MutableList<CustomerCart>,
    val onItemClick: CartItemListener
) :
    RecyclerView.Adapter<CartItemsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(val binding: ItemCartHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ItemCartHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val data = list[position]
        var currentQuantity = data.quantity
        holder.apply {
            binding.productTitle.text = data.product_name
            binding.qty.text = data.quantity.toString()
            binding.productId.text = data.product_id.toString()
            binding.productPrice.text = data.itemEndTotal.toString() + " JD"
            Glide.with(holder.itemView.context).load(data.product_image).into(binding.itemPic)
        }

        holder.binding.removeItem.setOnClickListener {
            onItemClick.onRemoveClick(position, data.id.toString())
//            if (holder.binding.changeItemCountLayout.visibility == View.VISIBLE) {
//                // Hide the other layout if it is visible
//                val slideOut =
//                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_down)
//                holder.binding.changeItemCountLayout.startAnimation(slideOut)
//                holder.binding.changeItemCountLayout.visibility = View.GONE
//                holder.binding.qty.text = data.quantity.toString()
//            }
//            if (holder.binding.removeItemLayout.visibility == View.GONE) {
//                holder.binding.removeItemLayout.visibility = View.VISIBLE
//                val slideIn =
//                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_up)
//                holder.binding.removeItemLayout.startAnimation(slideIn)
//            }
        }

        holder.binding.confirmRemoveItem.setOnClickListener {
            onItemClick.onRemoveClick(position, data.id.toString())
        }

        holder.binding.cancelRemoveItem.setOnClickListener {
            val slideOut =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_down)
            holder.binding.removeItemLayout.startAnimation(slideOut)
            holder.binding.removeItemLayout.visibility = View.GONE
        }

        holder.binding.plus.setOnClickListener {
            currentQuantity++
            holder.binding.qty.text = currentQuantity.toString() // Update UI
            if (holder.binding.removeItemLayout.visibility == View.VISIBLE) {
                // Hide the other layout if it is visible
                val slideOut =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_down)
                holder.binding.removeItemLayout.startAnimation(slideOut)
                holder.binding.removeItemLayout.visibility = View.GONE
            }
            if (holder.binding.changeItemCountLayout.visibility != View.VISIBLE) {
                holder.binding.changeItemCountLayout.visibility = View.VISIBLE
                val slideIn =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_up)
                holder.binding.changeItemCountLayout.startAnimation(slideIn)
            }
        }

        holder.binding.minus.setOnClickListener {
            if (currentQuantity > 1) {
                currentQuantity--
                holder.binding.qty.text = currentQuantity.toString()

                if (holder.binding.changeItemCountLayout.visibility != View.VISIBLE) {
                    holder.binding.changeItemCountLayout.visibility = View.VISIBLE
                    val slideIn =
                        AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_up)
                    holder.binding.changeItemCountLayout.startAnimation(slideIn)
                }
            }
        }

        holder.binding.cancelQuantityItem.setOnClickListener {
            if (holder.binding.changeItemCountLayout.visibility == View.VISIBLE) {
                val slideOut = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_down)
                slideOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        holder.binding.changeItemCountLayout.isClickable = false
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        holder.binding.changeItemCountLayout.visibility = View.GONE
                        holder.binding.changeItemCountLayout.isClickable = true
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                holder.binding.changeItemCountLayout.startAnimation(slideOut)
                holder.binding.qty.text = data.quantity.toString()
                currentQuantity = data.quantity
            }
        }

        holder.binding.confirmQuantityItem.setOnClickListener {
            onItemClick.onChangeQuantityClick(
                data.id.toString(),
                holder.binding.qty.text.toString()
            )
            val slideOut =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_down)
            holder.binding.changeItemCountLayout.startAnimation(slideOut)
            holder.binding.changeItemCountLayout.visibility = View.GONE
        }
    }


    fun removeItemAt(position: Int) {
        list.removeAt(position) // Remove the item from the data source
        notifyItemRemoved(position)  // Notify the adapter about the removal
    }


}