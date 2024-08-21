package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blueray.platin.R
import com.blueray.platin.adapters.OnProductListener
import com.blueray.platin.adapters.ProductsAdapter
import com.blueray.platin.databinding.FragmentSubCategoryDetailsBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.models.SubCategory
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class SubCategoryDetailsFragment : BaseFragment<FragmentSubCategoryDetailsBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private var category_id: Int? = null
    private var categoryName: String? = null
    private val sliderHandler = Handler(Looper.getMainLooper())
    private var sliderRunnable: Runnable? = null
    private var currentPage = 1
    private var lastPage = 1
    private val productsAdapter by lazy {
        ProductsAdapter(mutableListOf(), object : OnProductListener {
            override fun addToCart(pid: Int, quantity: String) {
                // Implementation here
            }

            override fun addToFavourite(pid: Int) {
                // Implementation here
            }

            override fun removeFromFavourite(favId: Int) {
                // Implementation here
            }

            override fun showDetails(pid: Int) {
                val bundle = Bundle().apply {
                    putString("pid", pid.toString())
                }
                navController?.navigate(
                    R.id.action_subCategoryDetailsFragment_to_productDetailsFragment,
                    bundle
                )
            }
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSubCategoryDetailsBinding {
        return FragmentSubCategoryDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryName = arguments?.getString("categoryName")
        navController = Navigation.findNavController(view)
        binding?.includeTab?.imageView2?.setBackgroundDrawable(resources.getDrawable(R.drawable.head_pic))
        binding?.includeTab?.title?.text = categoryName
        binding?.categoryName?.text = "منتجات " + categoryName
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }
        category_id = arguments?.getInt("category_id")

        setupRecyclerView()
        getProducts()
        binding?.priceSlider?.addOnChangeListener { slider, value, fromUser ->
            sliderRunnable?.let { sliderHandler.removeCallbacks(it) } // Remove any existing callbacks
            sliderRunnable = Runnable {
                // Action after delay
                val minPrice = slider.valueFrom
                val maxPrice = slider.value
                Log.d("SliderValue", "Slider value changed to $value")
//                toast(value.toString())
                currentPage = 1 // Reset the page number
                productsAdapter.clearProducts() // Clear existing products in adapter
                getProducts(minPrice, maxPrice) // Fetch new products with price filter
            }
            sliderHandler.postDelayed(sliderRunnable!!, 500) // Delay of 500ms
        }
    }



    private fun setupRecyclerView() {
        binding?.recyclerItem?.apply {
            setHasFixedSize(true)
            adapter = productsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == productsAdapter.itemCount - 1 && currentPage < lastPage) {
                        currentPage++
                        getProducts()
                    }
                }
            })
        }
    }

    private fun getProducts(minPrice: Float? = null, maxPrice: Float? = null) {
        if (currentPage > lastPage) return  // Prevent further API calls when the last page is reached

        // Pass minPrice and maxPrice if they are not null
        viewModel.retrieveProductsForCategory(category_id!!, currentPage, 8, minPrice, maxPrice)
        viewModel.getProductsForCategory().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.status == 200) {
                        currentPage = result.data.pagination.current_page
                        lastPage = result.data.pagination.last_page

                        val newProducts = result.data.data.products
                        val existingProducts = productsAdapter.getProducts()
                        val filteredProducts = newProducts.filter { newProduct ->
                            existingProducts.none { existingProduct -> existingProduct.id == newProduct.id }
                        }
                        productsAdapter.addProducts(filteredProducts)
                        if (result.data.data.sub_categories.isNullOrEmpty()) {
                            binding?.order?.visibility = View.GONE
                            binding?.spinnerOrder?.visibility = View.GONE
                        } else {
                            setupSpinner(result.data.data.sub_categories)
                        }
                    } else if (result.data.status == 404) {
                        toast("Empty Data")
                        Log.d("WERTYUioP", "EMPTYYYYYYYYYYYYY")
                    }
                }

                is NetworkResults.Error -> {
                    binding?.recyclerItem?.visibility = View.GONE
                    binding?.noItemsText?.visibility = View.VISIBLE
                }

                else -> {
                    // Handle other cases if necessary
                }
            }
        }
    }


    private fun setupSpinner(subCategories: List<SubCategory>) {
        val subCategoryNames = subCategories.map { it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            subCategoryNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinner?.adapter = adapter
    }
}


