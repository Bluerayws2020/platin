package com.blueray.platin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.blueray.platin.adapters.PicturesPagerAdapter
import com.blueray.platin.adapters.VideoPagerAdapter
import com.blueray.platin.databinding.FragmentMarketingBinding
import com.blueray.platin.ui.activities.MenuActivity
import com.blueray.platin.viewModels.AppViewModel


class MarketingFragment  : BaseFragment<FragmentMarketingBinding, AppViewModel>() {

    override val viewModel by viewModels<AppViewModel>()
    private var navController: NavController? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMarketingBinding {
        return FragmentMarketingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding?.includeTab?.title?.text = "المركز الإعلامي"
        binding?.includeTab?.back?.setOnClickListener {
            navController?.popBackStack()
        }
        binding?.includeTab?.menu?.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }


        val picturesPagerAdapter = PicturesPagerAdapter()
        binding?.viewPager?.adapter = picturesPagerAdapter
        binding?.viewPager?.setPageMargin(-400)
        binding?.viewPager?.offscreenPageLimit = 5
        binding?.viewPager?.post {
            binding!!.viewPager.setCurrentItem(1, true)
        }
        binding?.springDotsIndicator?.attachTo(binding!!.viewPager)


        val videoPagerAdapter = VideoPagerAdapter()
        binding?.viewPager2?.adapter = videoPagerAdapter
        binding?.viewPager2?.setPageMargin(-195)
        binding?.viewPager2?.offscreenPageLimit = 5
        binding?.viewPager2?.post {
            binding?.viewPager2?.setCurrentItem(1, true)
        }

        var viewPager1CurrentPage = 0
        var viewPager2CurrentPage = 0

        binding?.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                viewPager1CurrentPage = position
                binding?.viewPager2?.setCurrentItem(position, false)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding?.viewPager2?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                viewPager2CurrentPage = position
                binding?.viewPager?.setCurrentItem(position, false)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

}
