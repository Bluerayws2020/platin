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
import com.blueray.platin.adapters.CategoriesAdapter
import com.blueray.platin.adapters.onCategoryClick
import com.blueray.platin.databinding.FragmentHomeBinding
import com.blueray.platin.models.NetworkResults
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }


        viewModel.retrieveCategories()
        getCategories()


    }


    private fun getCategories() {
        viewModel.getCategories().observe(requireActivity()) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    categoriesAdapter =
                        CategoriesAdapter(result.data.data,
                            object : onCategoryClick {
                                override fun onItemClick(id:Int,position: Int) {
                                    val bundle = Bundle().apply {
                                        putInt("parent_id", id)
                                        putString("categoryName", result.data.data[position].name)
                                    }
                                    navController?.navigate(
                                        R.id.action_homeFragment_to_subCategoriesFragment,
                                        bundle
                                    )
                                }

                            })

                    binding?.recycler?.setHasFixedSize(true)
                    binding?.recycler?.adapter = categoriesAdapter
                }

                is NetworkResults.Error -> {

                }

                else -> {

                }
            }
        }
    }
}