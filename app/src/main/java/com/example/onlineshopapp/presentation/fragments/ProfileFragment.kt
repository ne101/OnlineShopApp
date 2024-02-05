package com.example.onlineshopapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.FragmentProfileBinding
import com.example.onlineshopapp.presentation.app.OnlineShopApplication
import com.example.onlineshopapp.presentation.viewModels.ProfileViewModel
import com.example.onlineshopapp.presentation.viewModels.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ProfileViewModel

    private val component by lazy {
        (requireActivity().application as OnlineShopApplication).component
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
        addCustomToolBar()
        launchFavouriteItemsScreen()
        removePersonInfoAndOut()
        observe()
    }

    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.text = "Личный кабинет"
        binding.customToolbar.ivBack.visibility = View.INVISIBLE
        binding.customToolbar.ivShare.visibility = View.INVISIBLE
    }

    private fun observe() {
        viewModel.favouriteItems.observe(viewLifecycleOwner) {
            binding.tvCount.text = it.items.count().toString()
        }
        viewModel.person.observe(viewLifecycleOwner) {
            binding.tvTitle.text = requireContext().getString(
                R.string.feedback_count,
                it.name,
                it.secondName
            )
            binding.tvPhone.text = it.phone
        }
    }

    private fun removePersonInfoAndOut() {
        binding.button2.setOnClickListener {
            viewModel.favouriteItems.observe(viewLifecycleOwner) { list ->
                list.items.forEach { item ->
                    viewModel.removeItemFromDB(item)
                }
            }

            viewModel.person.observe(viewLifecycleOwner) {
                viewModel.removePersonInfo(it.id)
                requireActivity().finish()
            }
        }
    }

    private fun launchFavouriteItemsScreen() {
        binding.cvFavourite.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favouriteItemsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }


}