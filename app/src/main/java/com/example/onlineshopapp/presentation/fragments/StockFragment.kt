package com.example.onlineshopapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.FragmentDetailInfoBinding
import com.example.onlineshopapp.databinding.FragmentStockBinding


class StockFragment : Fragment() {


    private var _binding: FragmentStockBinding? = null
    private val binding: FragmentStockBinding
        get() = _binding ?: throw RuntimeException("FragmentStockBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCustomToolBar()
    }

    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.text = "Акции"
        binding.customToolbar.ivBack.visibility = View.INVISIBLE
        binding.customToolbar.ivShare.visibility = View.INVISIBLE
    }
}