package com.example.onlineshopapp.presentation.fragments

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.FeatureBinding
import com.example.onlineshopapp.databinding.FirstPartDetailInfoBinding
import com.example.onlineshopapp.databinding.FragmentDetailInfoBinding
import com.example.onlineshopapp.databinding.SecondPartDetailInfoBinding
import com.example.onlineshopapp.databinding.ThirdPartDetailInfoBinding
import com.example.domain.domain.entities.ItemEntity
import com.example.onlineshopapp.presentation.adapter.ItemsAdapter
import com.example.onlineshopapp.presentation.app.OnlineShopApplication
import com.example.onlineshopapp.presentation.viewModels.CatalogViewModel
import com.example.onlineshopapp.presentation.viewModels.DetailInfoViewModel
import com.example.onlineshopapp.presentation.viewModels.ViewModelFactory
import javax.inject.Inject


class DetailInfoFragment : Fragment() {

    private val args by navArgs<DetailInfoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DetailInfoViewModel

    private val component by lazy {
        (requireActivity().application as OnlineShopApplication).component
    }
    private var _binding: FragmentDetailInfoBinding? = null
    private val binding: FragmentDetailInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailInfoBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailInfoViewModel::class.java]
        addCustomToolBar()
        viewModel.getItem(args.id)
        viewModel.item.observe(viewLifecycleOwner) {
            addDetailInfo(it)
        }
        backPressed()
    }

    private fun addDetailInfo(item: com.example.domain.domain.entities.ItemEntity) {
        val infoBinding = FirstPartDetailInfoBinding.inflate(
            layoutInflater,
            binding.llDetailInfo,
            false
        )
        addImageSlider(item, infoBinding)
        infoBinding.tvTitle.text = item.title
        infoBinding.tvSubTitle.text = item.subtitle
        infoBinding.tvPrice.paintFlags =
            infoBinding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        infoBinding.tvPrice.text = requireContext().getString(R.string.price, item.price.price)
        infoBinding.tvPriceWithDiscount.text =
            requireContext().getString(R.string.price, item.price.priceWithDiscount)
        infoBinding.tvDiscount.text = requireContext().getString(R.string.disc, item.price.discount)
        infoBinding.tvRating.text = item.feedback.rating.toString()
        addImageButtonHeart(item, infoBinding)
        addAvailableItem(item, infoBinding)
        addFeedback(item, infoBinding)
        addStars(item, infoBinding)
        binding.llDetailInfo.addView(infoBinding.root)

        val descriptionBinding = SecondPartDetailInfoBinding.inflate(
            layoutInflater,
            binding.llDetailInfo,
            false
        )
        descriptionBinding.tvDescription.text = item.description
        descriptionBinding.tvTitle.text = item.title
        descriptionBinding.tvHide.setOnClickListener {
            if (descriptionBinding.tvHide.text.toString() == "Скрыть") {
                descriptionBinding.tvDescription.visibility = View.GONE
                descriptionBinding.cardView4.visibility = View.GONE
                descriptionBinding.tvHide.text = "Подробнее"
            } else {
                descriptionBinding.tvDescription.visibility = View.VISIBLE
                descriptionBinding.cardView4.visibility = View.VISIBLE
                descriptionBinding.tvHide.text = "Скрыть"
            }
        }
        binding.llDetailInfo.addView(descriptionBinding.root)
        for (info in item.info) {
            val featureBinding = FeatureBinding.inflate(
                layoutInflater,
                binding.llDetailInfo,
                false
            )
            featureBinding.tvInfoTitle.text = info.title
            featureBinding.tvInfoValue.text = info.value
            binding.llDetailInfo.addView(featureBinding.root)
        }

        val compoundBinding = ThirdPartDetailInfoBinding.inflate(
            layoutInflater,
            binding.llDetailInfo,
            false
        )
        compoundBinding.tvPrice.paintFlags =
            compoundBinding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        compoundBinding.tvPrice.text = requireContext().getString(R.string.price, item.price.price)
        compoundBinding.tvIngredients.text = item.ingredients
        compoundBinding.tvPriceWithDiscount.text = requireContext().getString(R.string.price, item.price.priceWithDiscount)
        compoundBinding.tvHideOrUncover.setOnClickListener {
            if (compoundBinding.tvHideOrUncover.text.toString() == "Подробнее") {
                compoundBinding.tvHideOrUncover.text = "Скрыть"
                compoundBinding.tvIngredients.maxLines = Integer.MAX_VALUE
            } else {
                compoundBinding.tvHideOrUncover.text = "Подробнее"
                compoundBinding.tvIngredients.maxLines = 2
            }
        }
        binding.llDetailInfo.addView(compoundBinding.root)

    }

    private fun addStars(item: com.example.domain.domain.entities.ItemEntity, infoBinding: FirstPartDetailInfoBinding) {
        val emptyStar =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_another_empty_star)
        val halfStar =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_another_half_star)
        when {
            item.feedback.rating < 4.5 -> infoBinding.ivStar5.setImageDrawable(halfStar)
            item.feedback.rating < 4.0 -> infoBinding.ivStar5.setImageDrawable(emptyStar)
            item.feedback.rating < 3.5 -> infoBinding.ivStar4.setImageDrawable(halfStar)
            item.feedback.rating < 3.0 -> infoBinding.ivStar4.setImageDrawable(emptyStar)
            item.feedback.rating < 2.5 -> infoBinding.ivStar3.setImageDrawable(halfStar)
            item.feedback.rating < 2.0 -> infoBinding.ivStar3.setImageDrawable(emptyStar)
            item.feedback.rating < 1.5 -> infoBinding.ivStar2.setImageDrawable(halfStar)
            item.feedback.rating < 1.0 -> infoBinding.ivStar2.setImageDrawable(emptyStar)
            item.feedback.rating < 0.5 -> infoBinding.ivStar1.setImageDrawable(halfStar)
            item.feedback.rating == 0.0 -> infoBinding.ivStar1.setImageDrawable(emptyStar)
        }
    }

    private fun addFeedback(item: com.example.domain.domain.entities.ItemEntity, infoBinding: FirstPartDetailInfoBinding) {
        val word = when {
            item.feedback.count % 10 == 1 && item.feedback.count % 100 != 11 -> "отзыв"
            item.feedback.count % 10 in 2..4 && item.feedback.count % 100 !in 12..14 -> "отзыва"
            else -> "отзывов"
        }
        infoBinding.tvCount.text = requireContext().getString(
            R.string.feedback_count,
            item.feedback.count.toString(),
            word
        )
    }

    private fun addAvailableItem(item: com.example.domain.domain.entities.ItemEntity, infoBinding: FirstPartDetailInfoBinding) {
        val word = when {
            item.available % 10 == 1 && item.available % 100 != 11 -> "штука"
            item.available % 10 in 2..4 && item.available % 100 !in 12..14 -> "штуки"
            else -> "штук"
        }
        infoBinding.tvAvailable.text = requireContext().getString(
            R.string.available,
            item.available.toString(),
            word
        )
    }

    private fun addImageButtonHeart(item: com.example.domain.domain.entities.ItemEntity, infoBinding: FirstPartDetailInfoBinding) {
        viewModel.favouriteItems.observe(viewLifecycleOwner) {
            if (item in it.items) {
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart)
                infoBinding.ibHeart.setImageDrawable(drawable)
            } else {
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_empty_heart)
                infoBinding.ibHeart.setImageDrawable(drawable)
            }
        }
        viewModel.favouriteItems.observe(viewLifecycleOwner) {
            if (item in it.items) {
                infoBinding.ibHeart.setOnClickListener {
                    viewModel.removeItemFromBD(item)
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_empty_heart)
                    infoBinding.ibHeart.setImageDrawable(drawable)
                }
            } else {
                infoBinding.ibHeart.setOnClickListener {
                    viewModel.addItemInBD(item)
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart)
                    infoBinding.ibHeart.setImageDrawable(drawable)
                }
            }
        }
    }


    private fun addImageSlider(item: com.example.domain.domain.entities.ItemEntity, infoBinding: FirstPartDetailInfoBinding) {
        val imageList = HashMap<String, List<SlideModel>>()
        val slider1 = listOf(SlideModel(R.drawable.img_5), SlideModel(R.drawable.img_4))
        val slider2 = listOf(SlideModel(R.drawable.img), SlideModel(R.drawable.img_1))
        val slider3 = listOf(SlideModel(R.drawable.img_4), SlideModel(R.drawable.img_5))
        val slider4 = listOf(SlideModel(R.drawable.img_2), SlideModel(R.drawable.img_3))
        val slider5 = listOf(SlideModel(R.drawable.img_1), SlideModel(R.drawable.img_2))
        val slider6 = listOf(SlideModel(R.drawable.img_5), SlideModel(R.drawable.img))
        val slider7 = listOf(SlideModel(R.drawable.img_3), SlideModel(R.drawable.img_2))
        val slider8 = listOf(SlideModel(R.drawable.img), SlideModel(R.drawable.img_4))
        imageList[KEY_1] = slider1
        imageList[KEY_2] = slider2
        imageList[KEY_3] = slider3
        imageList[KEY_4] = slider4
        imageList[KEY_5] = slider5
        imageList[KEY_6] = slider6
        imageList[KEY_7] = slider7
        imageList[KEY_8] = slider8
        imageList[item.id]?.let { infoBinding.imageSlider.setImageList(it) }
    }

    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.visibility = View.INVISIBLE
        binding.customToolbar.ivBack.visibility = View.VISIBLE
        binding.customToolbar.ivShare.visibility = View.VISIBLE
        binding.customToolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (findNavController().currentDestination?.id == R.id.detailInfoFragment) {
                    findNavController().navigate(R.id.action_detailInfoFragment_to_catalogFragment)
                } else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    companion object {
        const val KEY_1 = "cbf0c984-7c6c-4ada-82da-e29dc698bb50"
        const val KEY_2 = "54a876a5-2205-48ba-9498-cfecff4baa6e"
        const val KEY_3 = "75c84407-52e1-4cce-a73a-ff2d3ac031b3"
        const val KEY_4 = "16f88865-ae74-4b7c-9d85-b68334bb97db"
        const val KEY_5 = "26f88856-ae74-4b7c-9d85-b68334bb97db"
        const val KEY_6 = "15f88865-ae74-4b7c-9d81-b78334bb97db"
        const val KEY_7 = "88f88865-ae74-4b7c-9d81-b78334bb97db"
        const val KEY_8 = "55f58865-ae74-4b7c-9d81-b78334bb97db"
    }
}