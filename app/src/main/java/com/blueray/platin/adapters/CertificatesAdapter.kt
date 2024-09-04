package com.blueray.platin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.databinding.CertificateItemBinding
import com.blueray.platin.models.GetCertificationsData
import com.bumptech.glide.Glide


// Interface for click listener

class CertificatesAdapter(
    var list: List<GetCertificationsData>
) : RecyclerView.Adapter<CertificatesAdapter.CertificateViewHolder>() {

    inner class CertificateViewHolder(val binding: CertificateItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val binding =
            CertificateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CertificateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        holder.apply {

            Glide.with(holder.itemView.context).load(list[position].image).into(binding.image)
        }
    }
}
