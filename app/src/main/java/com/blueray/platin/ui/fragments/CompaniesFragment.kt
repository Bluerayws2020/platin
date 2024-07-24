package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blueray.platin.R
import com.blueray.platin.adapters.CompaniesAdapter
import com.blueray.platin.adapters.PicturesAdapter
import com.blueray.platin.adapters.onCategoryClick
import com.blueray.platin.databinding.FragmentCompaniesBinding
import com.blueray.platin.databinding.FragmentPicturesBinding
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel

class CompaniesFragment : BaseFragment<FragmentCompaniesBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCompaniesBinding {
        return FragmentCompaniesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding.includeTab.title.text = "شركاتنا"
        binding.includeTab.back.setOnClickListener {
            navController?.popBackStack()
        }
        binding.includeTab.menu.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }


        val companiesAdapter = CompaniesAdapter(object : onCategoryClick {
            override fun onItemClick(position: Int) {
                navController?.navigate(R.id.action_homeFragment_to_subCategoriesFragment)
            }
        })
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = companiesAdapter


    }
}