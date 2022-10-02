package com.tunanh.clicktofood_hilt.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.ItemRecommendBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener


class RecommendAdapter :
    RecyclerView.Adapter<RecommendAdapter.MyViewHolder>() {


    inner class MyViewHolder constructor(val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            Glide.with(itemView.context).load(food.img).error(R.mipmap.ic_launcher)
                .into(binding.imgRecommend)
            binding.tvNameRecommend.text = food.title
            binding.tvRateCount.text = food.star.toString()
            updateLike(food)
        }

        fun updateLike(food: Food) {
            if (food.like) {
                binding.btnFavorite.setImageResource(R.drawable.ic_love)
            } else {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }


    var onClickLike: ((Food) -> Unit)? = null
    var onClickItem: ((Food) -> Unit)? = null
    var foodList: List<Food>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        foodList?.let { holder.bind(it[position]) }
        holder.itemView.setOnSingClickListener {
            foodList?.let { it1 -> onClickItem?.invoke(it1[position]) }
        }
        holder.binding.btnFavorite.setOnSingClickListener {
            foodList.also {
                it?.get(position)?.like = !it?.get(position)?.like!!
            }
            foodList?.let { it1 -> onClickLike?.invoke(it1[position]) }

            foodList?.let { it1 -> holder.updateLike(it1[position]) }
        }
    }

    override fun getItemCount(): Int = foodList?.size ?: 0
}