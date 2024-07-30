package com.blueray.platin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blueray.platin.databinding.FragmentTraderSignUpBinding
import com.blueray.platin.viewModels.AppViewModel


class TraderSignUpFragment(override val viewModel: AppViewModel) :
    BaseFragment<FragmentTraderSignUpBinding, AppViewModel>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTraderSignUpBinding {
        return FragmentTraderSignUpBinding.inflate(inflater, container, false)
    }


}