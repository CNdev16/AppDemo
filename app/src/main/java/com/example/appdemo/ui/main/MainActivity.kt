package com.example.appdemo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appdemo.R
import com.example.appdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nevHost) as NavHostFragment
        val navController = navHostFragment.navController
        _binding.navigation.setupWithNavController(navController)
    }
}