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
import com.blueray.platin.adapters.SubCategoriesAdapter
import com.blueray.platin.adapters.onCategoryClick
import com.blueray.platin.databinding.FragmentSubCategoriesBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class SubCategoriesFragment : BaseFragment<FragmentSubCategoriesBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private var parent_id: Int? = null
    private var categoryName: String? = null
    private lateinit var subCategoriesAdapter: SubCategoriesAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSubCategoriesBinding {
        return FragmentSubCategoriesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        parent_id = arguments?.getInt("parent_id")
        categoryName = arguments?.getString("categoryName")
        binding?.categoryName?.text = categoryName
        viewModel.retrieveSubCategories(parent_id!!)
        getSubCategories()


    }

    private fun getSubCategories() {
        viewModel.getSubCategories().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    subCategoriesAdapter =
                        SubCategoriesAdapter(result.data.data, object : onCategoryClick {
                            override fun onItemClick(id: Int, position: Int) {
                                val bundle = Bundle().apply {
                                    putInt("category_id", id)
                                    putString("categoryName", result.data.data[position].name)
                                }
                                navController?.navigate(
                                    R.id.action_subCategoriesFragment_to_subCategoryDetailsFragment,
                                    bundle
                                )
                            }

                        })

                    binding?.recycler?.setHasFixedSize(true)
                    binding?.recycler?.adapter = subCategoriesAdapter
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }

}


