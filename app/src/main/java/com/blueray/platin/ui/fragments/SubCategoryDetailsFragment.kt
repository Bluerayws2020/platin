package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
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
    private var selectedSubCategoryId: Int? = null // New field to track selected sub-category
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
                    putInt("category_id", category_id!!)
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

        getProducts()  // Initial call without price parameters

        // Set up slider listener with delay
        binding?.priceSlider?.addOnChangeListener { slider, value, fromUser ->
            sliderRunnable?.let { sliderHandler.removeCallbacks(it) } // Remove any existing callbacks
            sliderRunnable = Runnable {
                // Action after delay
                val minPrice = slider.valueFrom
                val maxPrice = slider.value
                Log.d("SliderValue", "Slider value changed to $value")

                currentPage = 1 // Reset the page number
                productsAdapter.clearProducts() // Clear existing products in adapter
                getProducts(minPrice, maxPrice) // Fetch new products with price filter
            }
            sliderHandler.postDelayed(sliderRunnable!!, 500) // Delay of 500ms
        }

        binding?.searchView?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Get the search text from EditText
                val searchText = v.text.toString()

                // Use the search text with the API call
                if (searchText.isNotEmpty()) {
                    currentPage = 1 // Reset page for new search
                    productsAdapter.clearProducts() // Clear existing products
                    getProducts(searchText = searchText)
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.recyclerItem?.apply {
            Log.d("WaleedKHA", productsAdapter.itemCount.toString())
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == productsAdapter.itemCount - 1 && currentPage < lastPage) {
                        currentPage++
                        getProducts()  // Paginate without price filter
                    }
                }
            })
        }
    }

    private fun getProducts(
        minPrice: Float? = null,
        maxPrice: Float? = null,
        searchText: String? = null
    ) {
        if (currentPage > lastPage) return  // Prevent further API calls when the last page is reached

        // Modify the API call to include search text if available
        viewModel.retrieveProductsForCategory(
            category_id!!,
            currentPage,
            8,
            minPrice,
            maxPrice,
            searchText
        )

        // Observe LiveData with LifecycleOwner
        viewModel.getProductsForCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.status == 200) {
                        currentPage = result.data.pagination.current_page
                        lastPage = result.data.pagination.last_page
                        setupRecyclerView()
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
        // Add a placeholder item for "Select Sub-Category"
        val subCategoryNames = mutableListOf("اختر التصنيف الفرعي")
        subCategoryNames.addAll(subCategories.map { it.name })
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            subCategoryNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinner?.adapter = adapter

        binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    category_id = subCategories[position - 1].id
                    currentPage = 1
                    productsAdapter.clearProducts()
                    getProducts()
                } else {
                    selectedSubCategoryId = null
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}
