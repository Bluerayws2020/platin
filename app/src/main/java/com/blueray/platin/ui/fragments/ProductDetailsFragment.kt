package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.R
import com.blueray.platin.adapters.OffersAdapter
import com.blueray.platin.adapters.OnProductListener
import com.blueray.platin.adapters.ProductsAdapter
import com.blueray.platin.adapters.SubCategoriesAdapter
import com.blueray.platin.adapters.onCategoryClick
import com.blueray.platin.databinding.FragmentProductDetailsBinding
import com.blueray.platin.databinding.FragmentSubCategoriesBinding
import com.blueray.platin.databinding.FragmentSubCategoryDetailsBinding
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailsBinding {
        return FragmentProductDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding.includeTab.imageView2.setBackgroundDrawable(resources.getDrawable(R.drawable.head_pic))
        binding.includeTab.title.text = "حماية الرأس"
        binding.includeTab.back.setOnClickListener {
            navController?.popBackStack()
        }
        binding.includeTab.menu.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

        val productAdapter = ProductsAdapter(object : OnProductListener {
            override fun addToCart(pid: Int, quantity: String) {
                TODO("Not yet implemented")
            }

            override fun addToFavourite(pid: Int) {
                TODO("Not yet implemented")
            }

            override fun removeFromFavourite(favId: Int) {
                TODO("Not yet implemented")
            }

            override fun showDetails(position: Int) {
//                navController?.navigate(R.id.action_subCategoriesFragment_to_subCategoryDetailsFragment)
            }

        })

        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = productAdapter


        val offersAdapter = OffersAdapter(object : OnProductListener {
            override fun addToCart(pid: Int, quantity: String) {
                TODO("Not yet implemented")
            }

            override fun addToFavourite(pid: Int) {
                TODO("Not yet implemented")
            }

            override fun removeFromFavourite(favId: Int) {
                TODO("Not yet implemented")
            }

            override fun showDetails(position: Int) {
//                navController?.navigate(R.id.action_subCategoriesFragment_to_subCategoryDetailsFragment)
            }

        })

        binding.recycler2.setHasFixedSize(true)
        binding.recycler2.adapter = offersAdapter


    }



}


