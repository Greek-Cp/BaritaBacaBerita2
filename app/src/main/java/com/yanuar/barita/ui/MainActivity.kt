package com.yanuar.barita.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.yanuar.barita.R
import com.yanuar.barita.databinding.ActivityMainBinding
import com.yanuar.barita.ui.about.AboutFragment
import com.yanuar.barita.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idNavBottom.setOnItemSelectedListener {
            item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("TAG","select nav home")
                    selectedFragment = HomeFragment()
                }
                R.id.navigation_about -> {

                    Log.d("TAG","select nav about")
                    selectedFragment = AboutFragment()
                }
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.id_frame_layout, selectedFragment)
                    .commit()
            }
            true
        }
    }
}