package com.example.appdemo.ui.main.fragment.first

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.appdemo.R
import com.example.appdemo.databinding.FragmentFirstBinding
import com.example.appdemo.ui.place.PlaceListActivity

class FirstFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        mutableListOf<ImageView>(
            _binding.view1,
            _binding.view2,
            _binding.view3,
            _binding.view4,
            _binding.view5
        ).run {
            this.forEach { v ->
                v.setOnClickListener {
                    startActivity(Intent(requireActivity(), PlaceListActivity::class.java))
                }
            }
        }
    }
}