package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.blueray.platin.R
import com.blueray.platin.adapters.OffersAdapter
import com.blueray.platin.adapters.OnProductListener
import com.blueray.platin.adapters.ProductsAdapter
import com.blueray.platin.adapters.ThumbnailAdapter
import com.blueray.platin.databinding.FragmentProductDetailsBinding
import com.blueray.platin.models.Color
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.models.Size
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel
import com.denzcoskun.imageslider.models.SlideModel

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private lateinit var pid: String
    private lateinit var images: MutableList<String>
    private var sizeId = ""
    private var colorId = ""
    private var variationId = ""
    private var variationType = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailsBinding {
        return FragmentProductDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pid = arguments?.getString("pid").toString()
        navController = Navigation.findNavController(view)
        binding?.includeTab?.imageView2?.setBackgroundDrawable(resources.getDrawable(R.drawable.head_pic))
        binding?.includeTab?.title?.text = "حماية الرأس"
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

        binding?.addToCartButton?.setOnClickListener {
            addToCartOptions()
        }

        viewModel.retrieveProductDetails(pid)
        getProductsDetails()
        images = mutableListOf()
        setupQuantityButtons()
        // Setup adapters for existing recycler views
        setupProductRecyclerView()
        setupOffersRecyclerView()

        getColorsBySize()
        getVariationPrice()
        addToCart()
    }

    private fun setupProductRecyclerView() {
        val productAdapter = ProductsAdapter(mutableListOf(), object : OnProductListener {
            override fun addToCart(pid: Int, quantity: String) {}
            override fun addToFavourite(pid: Int) {}
            override fun removeFromFavourite(favId: Int) {}
            override fun showDetails(position: Int) {}
        })
        binding?.recycler?.apply {
            setHasFixedSize(true)
            adapter = productAdapter
        }
    }

    private fun setupOffersRecyclerView() {
        val offersAdapter = OffersAdapter(object : OnProductListener {
            override fun addToCart(pid: Int, quantity: String) {}
            override fun addToFavourite(pid: Int) {}
            override fun removeFromFavourite(favId: Int) {}
            override fun showDetails(position: Int) {}
        })
        binding?.recycler2?.apply {
            setHasFixedSize(true)
            adapter = offersAdapter
        }
    }

    private fun getProductsDetails() {
        viewModel.getProductDetails().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    val productData = result.data.data
                    binding?.categoryName?.text = productData.name
                    variationType = productData.variation_type
                    if (productData.description.isNullOrEmpty()) {
                        binding?.description?.visibility = View.GONE
                        binding?.descriptionTv?.visibility = View.GONE
                    } else {
                        binding?.description?.text =
                            Html.fromHtml(productData.description, Html.FROM_HTML_MODE_COMPACT)
                    }
                    images.add(productData.image)
                    if (productData.other_images.isNotEmpty()) {
                        images.addAll(productData.other_images)
                        setUpImageSlider()
                    } else {
                        setUpImageSlider()
                        binding?.recyclerViewThumbnails?.visibility = View.GONE
                    }

                    if (productData.variation_type == "Standard") {
                        setupSizeSpinner(productData.productVariations.size)
                        setupColorSpinner(productData.productVariations.color)
                        binding?.price?.text = productData.productVariations.price + " JD"
                    } else {
                        setupSizeSpinnerForMultiple(productData.productVariations.size)
                    }

                }

                is NetworkResults.Error -> {
                    // Handle the error
                }

                is NetworkResults.NoInternet -> {
                    // Handle no internet
                }
            }
        }
    }

    private fun setupSizeSpinner(sizes: List<Size>) {
        val sizeNames = sizes.map { it.name }
        val sizeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sizeNames)
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.sizeSpinner?.adapter = sizeAdapter

        binding?.sizeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                sizeId = sizes[position].size_id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no item is selected if needed
            }
        }
    }

    private fun setupColorSpinner(colors: List<Color>) {
        val colorNames = colors.map { it.name }
        val colorAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorNames)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.colorSpinner?.adapter = colorAdapter

        binding?.colorSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    colorId = colors[position].color_id.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun setupSizeSpinnerForMultiple(sizes: List<Size>) {
        val sizeNames = sizes.map { it.name }
        val sizeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sizeNames)
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.sizeSpinner?.adapter = sizeAdapter

        binding?.sizeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                sizeId = sizes[position].size_id.toString()
                viewModel.retrieveColorsBySize(sizeId, pid)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no item is selected if needed
            }
        }

    }

    private fun setupColorSpinnerForMultiple(colors: List<Color>) {
        val colorNames = colors.map { it.name }
        val colorAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorNames)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.colorSpinner?.adapter = colorAdapter

        binding?.colorSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    colorId = colors[position].color_id.toString()
                    viewModel.retrieveVariationPrice(
                        product_id = pid,
                        size_id = sizeId,
                        color_id = colorId
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }


    private fun setUpImageSlider() {
        val slider = binding?.imageSlider
        slider?.setImageList(images.map { SlideModel(it) })

        val thumbnailAdapter = ThumbnailAdapter(images) { position ->
            val viewPager =
                slider?.findViewById<androidx.viewpager.widget.ViewPager>(R.id.view_pager)
            viewPager?.setCurrentItem(position, true)
        }
        binding?.recyclerViewThumbnails?.apply {
            adapter = thumbnailAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getColorsBySize() {
        viewModel.getColorsBySize().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    setupColorSpinnerForMultiple(result.data.data)
                }

                is NetworkResults.Error -> {

                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }

    private fun getVariationPrice() {
        viewModel.getVariationPrice().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    binding?.price?.text = result.data.data.price
                    variationId = result.data.data.id.toString()
                }

                is NetworkResults.Error -> {

                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }

    private fun setupQuantityButtons() {
        binding?.quantity?.setText("1")

        binding?.plusButton?.setOnClickListener {
            val currentQuantity = binding?.quantity?.text.toString().toIntOrNull() ?: 0
            binding?.quantity?.setText((currentQuantity + 1).toString())
        }

        binding?.minusButton?.setOnClickListener {
            val currentQuantity = binding?.quantity?.text.toString().toIntOrNull() ?: 0
            if (currentQuantity > 1) {
                binding?.quantity?.setText((currentQuantity - 1).toString())
            } else {
                binding?.quantity?.setText("1")
            }
        }
    }

    private fun addToCartOptions() {
        if (variationType == "Standard") {
            viewModel.retrieveAddToCart(
                product_id = pid,
                size_id = sizeId,
                color_id = colorId,
                quantity = binding?.quantity?.text.toString(),
                variation_id = null
            )
        } else {
            viewModel.retrieveAddToCart(
                product_id = pid,
                size_id = sizeId,
                color_id = colorId,
                quantity = binding?.quantity?.text.toString(),
                variation_id = variationId
            )
        }
    }

    private fun addToCart() {
        viewModel.getAddToCart().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    toast(result.data.message)
                }

                is NetworkResults.Error -> {
                    toast(result.exception.localizedMessage.toString())
                }

                is NetworkResults.NoInternet -> {

                }
            }
        }
    }
}
