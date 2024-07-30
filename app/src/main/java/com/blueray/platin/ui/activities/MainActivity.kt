package com.blueray.platin.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.blueray.platin.R
import com.blueray.platin.databinding.ActivityMainBinding
import com.blueray.platin.databinding.FragmentAboutCompanyBinding
import com.blueray.platin.ui.fragments.AboutCompanyFragment
import com.blueray.platin.ui.fragments.AccountFragment
import com.blueray.platin.ui.fragments.CartFragment
import com.blueray.platin.ui.fragments.ContactUsFragment
import com.blueray.platin.ui.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    var frag: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.accountFragment,
            R.id.contactUsFragment,
            R.id.cartFragment
        ))
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id == R.id.homeFragment) {
//                navController.popBackStack(R.id.homeFragment, false)
//
//            }
//        }
        binding.bottomNav.setOnItemSelectedListener { item ->
            navController.popBackStack(R.id.homeFragment, false)
            NavigationUI.onNavDestinationSelected(item, navController)
            true
        }

        val bundle = intent.extras
        frag = bundle?.getString("fragment")
        if (frag == "aboutCompany") {
            navController.navigate(R.id.aboutCompanyFragment)
        }
        if (frag == "marketing") {
            navController.navigate(R.id.marketingFragment)
        }
        if (frag == "achivements") {
            navController.navigate(R.id.certificatesFragment)
        }
        if (frag == "brands") {
            navController.navigate(R.id.brandsFragment)
        }
        if (frag == "companies") {
            navController.navigate(R.id.companiesFragment)
        }

        Log.e("*****", frag.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}