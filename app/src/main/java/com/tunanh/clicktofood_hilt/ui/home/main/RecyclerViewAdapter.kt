package com.tunanh.clicktofood_hilt.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.ItemRecyclerview2Binding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener


class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder constructor(private val binding: ItemRecyclerview2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            Glide.with(itemView.context).load(food.img).error(R.mipmap.ic_launcher)
                .into(binding.imgItem2)
            binding.nameItem.text = food.title
            binding.tvRating.text = food.star.toString()
        }
    }
    var onClickItem: ((Food) -> Unit)? = null
    var foodList: List<Food>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            ItemRecyclerview2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        foodList?.let { holder.bind(it[position]) }
        holder.itemView.setOnSingClickListener {
            foodList?.let { it1 -> onClickItem?.invoke(it1[position]) }
        }
    }

    override fun getItemCount(): Int = foodList?.size ?: 0
}