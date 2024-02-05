package com.example.onlineshopapp.presentation.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.denzcoskun.imageslider.models.SlideModel
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.ItemCardBinding
import com.example.onlineshopapp.domain.entities.ItemEntity


interface OnClickListener {
    fun onHeartClickAdd(item: ItemEntity)
    fun onHeartClickRemove(item: ItemEntity)
    fun checkItemInDBOrNot(item: ItemEntity): Boolean
    fun onItemClick(item: ItemEntity)
}

class ItemsAdapter(private val heartClickListener: OnClickListener) :
    ListAdapter<ItemEntity, ItemsViewHolder>(ItemsDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
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
        val binding = holder.binding
        val context = holder.itemView.context
        val currentItem = getItem(position)
        imageList[currentItem.id]?.let { binding.imageSlider.setImageList(it) }
        binding.tvPrice.paintFlags = binding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.tvPrice.text = context.getString(R.string.price, currentItem.price.price)
        binding.tvDiscount.text = context.getString(R.string.disc, currentItem.price.discount)
        binding.tvPriceWithDiscount.text = context.getString(
            R.string.price,
            currentItem.price.priceWithDiscount
        )
        binding.tvSubTitle.text = currentItem.subtitle
        binding.tvRating.text = currentItem.feedback.rating.toString()
        binding.tvTitle.text = currentItem.title
        binding.tvCount.text = context.getString(R.string.count, currentItem.feedback.count.toString())

        if (heartClickListener.checkItemInDBOrNot(currentItem)) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart)
            binding.ibHeart.setImageDrawable(drawable)
        } else {
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_empty_heart)
            binding.ibHeart.setImageDrawable(drawable)
        }

        binding.ibHeart.setOnClickListener {
            if (!heartClickListener.checkItemInDBOrNot(currentItem)) {
                Log.d("ItemsAdapter","Добавил")
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart)
                binding.ibHeart.setImageDrawable(drawable)
                heartClickListener.onHeartClickAdd(currentItem)

            } else {
                Log.d("ItemsAdapter","Удалил")
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_empty_heart)
                binding.ibHeart.setImageDrawable(drawable)
                heartClickListener.onHeartClickRemove(currentItem)
            }
        }
        binding.root.setOnClickListener {
            heartClickListener.onItemClick(currentItem)
        }
    }

    companion object {
        private const val KEY_1 = "cbf0c984-7c6c-4ada-82da-e29dc698bb50"
        private const val KEY_2 = "54a876a5-2205-48ba-9498-cfecff4baa6e"
        private const val KEY_3 = "75c84407-52e1-4cce-a73a-ff2d3ac031b3"
        private const val KEY_4 = "16f88865-ae74-4b7c-9d85-b68334bb97db"
        private const val KEY_5 = "26f88856-ae74-4b7c-9d85-b68334bb97db"
        private const val KEY_6 = "15f88865-ae74-4b7c-9d81-b78334bb97db"
        private const val KEY_7 = "88f88865-ae74-4b7c-9d81-b78334bb97db"
        private const val KEY_8 = "55f58865-ae74-4b7c-9d81-b78334bb97db"
    }
}
