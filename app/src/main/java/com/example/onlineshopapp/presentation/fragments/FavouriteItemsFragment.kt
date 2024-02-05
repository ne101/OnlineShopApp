package com.example.onlineshopapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.FragmentCatalogBinding
import com.example.onlineshopapp.databinding.FragmentFavouriteItemsBinding
import com.example.onlineshopapp.domain.entities.ItemEntity
import com.example.onlineshopapp.presentation.adapter.ItemsAdapter
import com.example.onlineshopapp.presentation.adapter.OnClickListener
import com.example.onlineshopapp.presentation.app.OnlineShopApplication
import com.example.onlineshopapp.presentation.viewModels.CatalogViewModel
import com.example.onlineshopapp.presentation.viewModels.FavouriteItemsViewModel
import com.example.onlineshopapp.presentation.viewModels.ViewModelFactory
import javax.inject.Inject

class FavouriteItemsFragment : Fragment(), OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FavouriteItemsViewModel

    private val component by lazy {
        (requireActivity().application as OnlineShopApplication).component
    }
    private var _binding: FragmentFavouriteItemsBinding? = null
    private val binding: FragmentFavouriteItemsBinding
        get() = _binding ?: throw RuntimeException("FragmentFavouriteItemsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavouriteItemsViewModel::class.java]
        addCustomToolBar()
        val adapter = ItemsAdapter(this)
        binding.rvTtems.adapter = adapter
        binding.rvTtems.layoutManager = GridLayoutManager(context, 2)
        showItems(adapter)
    }

    private fun showItems(adapter: ItemsAdapter) {
        viewModel.favouriteItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.text = "Избранное"
        binding.customToolbar.ivBack.visibility = View.VISIBLE
        binding.customToolbar.ivShare.visibility = View.INVISIBLE
        binding.customToolbar.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_favouriteItemsFragment_to_profileFragment)
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

    override fun onHeartClickAdd(item: ItemEntity) {
        viewModel.addItemInDB(item)
    }

    override fun onHeartClickRemove(item: ItemEntity) {
        viewModel.removeItemFromDB(item)
    }

    override fun checkItemInDBOrNot(item: ItemEntity): Boolean {
        val favouriteItems = viewModel.favouriteItems.value
        return favouriteItems?.any { it.id == item.id } ?: false
    }

    override fun onItemClick(item: ItemEntity) {
        findNavController().navigate(FavouriteItemsFragmentDirections.actionFavouriteItemsFragmentToDetailInfoFragment(item.id))
    }
}