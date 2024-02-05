package com.example.onlineshopapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.FragmentMenuBinding
import com.example.onlineshopapp.databinding.FragmentStockBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCustomToolBar()
    }

    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.text = "Главная"
        binding.customToolbar.ivBack.visibility = View.INVISIBLE
        binding.customToolbar.ivShare.visibility = View.INVISIBLE
    }
}