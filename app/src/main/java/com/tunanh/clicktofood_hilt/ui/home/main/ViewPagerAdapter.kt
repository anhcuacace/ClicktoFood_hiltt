package com.tunanh.clicktofood_hilt.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunanh.clicktofood.data.remote.model.Slider
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.databinding.ItemSlideBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    inner class PagerViewHolder constructor(private val binding: ItemSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(slider: Slider) {
            Glide.with(itemView.context).load(slider.img).error(R.mipmap.ic_launcher)
                .into(binding.imgSlide)

        }
    }

    var sliderList: List<Slider>? = null
    var onClickItem: ((Slider) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding =
            ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        sliderList?.get(position)?.let { holder.bind(it) }
        holder.itemView.setOnSingClickListener {
            sliderList?.let { it1 -> onClickItem?.invoke(it1[position]) }
        }
    }

    override fun getItemCount(): Int {
        return sliderList?.size ?: 0
    }
}