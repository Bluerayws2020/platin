package com.blueray.platin.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blueray.platin.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.aboutCompany.setOnClickListener{
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("fragment", "aboutCompany")
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.marketing.setOnClickListener{
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("fragment", "marketing")
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.achivements.setOnClickListener{
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("fragment", "achivements")
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.brands.setOnClickListener{
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("fragment", "brands")
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.companies.setOnClickListener{
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("fragment", "companies")
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }
}