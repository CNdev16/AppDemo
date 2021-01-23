package com.example.appdemo.ui.main.fragment.second

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.appdemo.R
import com.example.appdemo.databinding.FragmentSecondBinding
import com.example.appdemo.ui.place.PlaceListActivity

class SecondFragment : Fragment() {

    private lateinit var _binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container ,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        mutableListOf<LinearLayout>(
            _binding.groupView1,
            _binding.groupView2,
            _binding.groupView3,
            _binding.groupView4,
            _binding.groupView5
        ).run {
            this.forEach { v ->
                v.setOnClickListener {
                    startActivity(Intent(requireActivity(), PlaceListActivity::class.java))
                }
            }
        }
    }
}