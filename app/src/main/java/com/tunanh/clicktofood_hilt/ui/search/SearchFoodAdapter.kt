package com.tunanh.clicktofood_hilt.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.ItemTempBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class SearchFoodAdapter : RecyclerView.Adapter<SearchFoodAdapter.FoodViewHolder>() {
    var onClickItem: ((Food) -> Unit)? = null
    private var foodList: List<Food>? = null
    var onClickAdd: ((Food) -> Unit)? = null
    var onClickLike: ((Food) -> Unit)? = null

    class FoodViewHolder constructor(val binding: ItemTempBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            Glide.with(itemView.context).load(food.img).error(R.mipmap.ic_launcher)
                .into(binding.imgFood)
            binding.tvTitleItem.text = food.title
            binding.cost.text = "${food.cost} $"
            updateLike(food)
        }

        fun updateLike(food: Food) {
            if (food.like) {
                binding.btnLike.setImageResource(R.drawable.ic_love)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val v =
            ItemTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(v)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList!![position])
        holder.itemView.setOnSingClickListener {
            onClickItem?.invoke(foodList!![position])
        }
        holder.binding.tvAdd.setOnSingClickListener {
            onClickAdd?.invoke(foodList!![position])
        }
        holder.binding.btnLike.setOnSingClickListener {
            foodList.also {
                it?.get(position)?.like = !it?.get(position)?.like!!
            }
            foodList?.let { it1 -> holder.updateLike(it1[position]) }
            foodList?.let { it1 -> onClickLike?.invoke(it1[position]) }
        }
    }

    override fun getItemCount(): Int = if (foodList != null) foodList?.size!! else 0

    @SuppressLint("NotifyDataSetChanged")
    fun addFoodList(foodList: List<Food>) {
        this.foodList = foodList
        notifyDataSetChanged()
    }
}