package com.example.onlineshopapp.presentation.fragments

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlineshopapp.databinding.FilterCardBinding
import com.example.onlineshopapp.databinding.FragmentCatalogBinding
import com.example.domain.domain.entities.ItemEntity
import com.example.onlineshopapp.presentation.adapter.ItemsAdapter
import com.example.onlineshopapp.presentation.adapter.OnClickListener
import com.example.onlineshopapp.presentation.app.OnlineShopApplication
import com.example.onlineshopapp.presentation.viewModels.CatalogViewModel
import com.example.onlineshopapp.presentation.viewModels.ViewModelFactory
import javax.inject.Inject


class CatalogFragment : Fragment(), OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CatalogViewModel

   private var selectedTag = "Смотреть все"

    private val component by lazy {
        (requireActivity().application as OnlineShopApplication).component
    }
    private var _binding: FragmentCatalogBinding? = null
    private val binding: FragmentCatalogBinding
        get() = _binding ?: throw RuntimeException("FragmentCatalogBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[CatalogViewModel::class.java]
        addCustomToolBar()
        val adapter = ItemsAdapter(this)
        addSpinner(adapter)
        addFilter(adapter)
        binding.rvTtems.adapter = adapter
        binding.rvTtems.layoutManager = GridLayoutManager(context, 2)
        showItems(adapter, binding.spinner.selectedItem.toString())

    }

    private fun addFilter(adapter: ItemsAdapter) {
        val list = listOf("Лицо", "Тело", "Загар", "Маски")
        val filterViews = mutableListOf<FilterCardBinding>()
        val paddingInDp = 12
        val density = resources.displayMetrics.density
        val paddingInPx = (paddingInDp * density).toInt()
        if (binding.llFilter.childCount > 0) {
            for (i in 0 until binding.llFilter.childCount) {
                val filterBinding = FilterCardBinding.bind(binding.llFilter.getChildAt(i))
                filterBinding.tvFilter.text = list[i]
                filterViews.add(filterBinding)
            }
        } else {
            val firstElement = FilterCardBinding.inflate(
                layoutInflater,
                binding.llFilter,
                false
            )
            binding.llFilter.addView(firstElement.root)
            filterViews.add(firstElement)

            for (tag in list) {
                val filterBinding = FilterCardBinding.inflate(
                    layoutInflater,
                    binding.llFilter,
                    false
                )
                binding.llFilter.addView(filterBinding.root)
                filterBinding.tvFilter.text = tag
                filterBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                filterBinding.tvFilter.setTextColor(Color.parseColor("#A0A1A3"))
                filterBinding.imageView4.visibility = View.GONE
                filterBinding.tvFilter.updatePadding(right = paddingInPx)
                filterViews.add(filterBinding)

            }
        }

        // Устанавливаем OnClickListener
        for (filterBinding in filterViews) {
            filterBinding.filterCardView.setOnClickListener {

                filterBinding.tvFilter.setTextColor(Color.WHITE)
                filterBinding.tvFilter.updatePadding(right = paddingInPx/2)
                filterBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#52606D"))
                filterBinding.imageView4.visibility = View.VISIBLE

                // Сбрасываем цвет всех остальных элементов
                for (otherBinding in filterViews) {
                    if (otherBinding != filterBinding) {
                        otherBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                        otherBinding.tvFilter.setTextColor(Color.parseColor("#A0A1A3"))
                        otherBinding.tvFilter.updatePadding(right = paddingInPx)
                        otherBinding.imageView4.visibility = View.GONE
                    }
                }
                // Сохраняем выбранный элемент
                selectedTag = filterBinding.tvFilter.text.toString()
                showItems(adapter, binding.spinner.selectedItem.toString())
            }

            filterBinding.imageView4.setOnClickListener {
                // Сбрасываем цвет всех элементов
                for (otherBinding in filterViews) {
                    otherBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                    otherBinding.tvFilter.setTextColor(Color.parseColor("#A0A1A3"))
                    otherBinding.tvFilter.updatePadding(right = paddingInPx)
                    otherBinding.imageView4.visibility = View.GONE
                }
                // Сохраняем выбранный элемент как "Смотреть все"
                selectedTag = "Смотреть все"
                showItems(adapter, binding.spinner.selectedItem.toString())
            }
        }
    }


    private fun showItems(
        adapter: ItemsAdapter,
        sortType: String,
    ) {
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(viewModel.filterAndSort(it.items, sortType, selectedTag))
        }
    }

    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.text = "Каталог"
        binding.customToolbar.ivBack.visibility = View.INVISIBLE
        binding.customToolbar.ivShare.visibility = View.INVISIBLE
    }

    private fun addSpinner(adapter: ItemsAdapter) {
        val list = arrayOf("По популярности", "По уменьшению цены", "По возрастанию цены")
        val adapterSpinner = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, list)
        adapterSpinner.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.let {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    showItems(adapter, selectedItem)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
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

    override fun onHeartClickAdd(item: com.example.domain.domain.entities.ItemEntity) {
        viewModel.addItemInDB(item)
    }

    override fun checkItemInDBOrNot(item: com.example.domain.domain.entities.ItemEntity): Boolean {
        val favouriteItems = viewModel.favouriteItems.value
        return favouriteItems?.any { it.id == item.id } ?: false
    }

    override fun onItemClick(item: com.example.domain.domain.entities.ItemEntity) {
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToDetailInfoFragment(item.id))
    }


    override fun onHeartClickRemove(item: com.example.domain.domain.entities.ItemEntity) {
        viewModel.removeItemFromDB(item)
    }
}