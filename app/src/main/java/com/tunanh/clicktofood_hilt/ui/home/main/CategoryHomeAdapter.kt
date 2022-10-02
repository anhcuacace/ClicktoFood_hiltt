package com.tunanh.clicktofood_hilt.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.data.remote.model.Category
import com.tunanh.clicktofood_hilt.databinding.ItemCategoryHomeBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener


class CategoryHomeAdapter :
    RecyclerView.Adapter<CategoryHomeAdapter.MyViewHolder>() {
    inner class MyViewHolder constructor(private val binding: ItemCategoryHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            Glide.with(itemView.context).load(category.image).error(R.mipmap.ic_launcher)
                .into(binding.imgItem)
            binding.tvTitle.text = category.title
        }
    }

    var categoryList: List<Category>? = null
    var onClickItem: ((Category) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = ItemCategoryHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        categoryList?.let { holder.bind(it[position]) }
        holder.itemView.setOnSingClickListener {
            categoryList?.let { it1 -> onClickItem?.invoke(it1[position]) }
        }
    }

    override fun getItemCount(): Int = categoryList?.size ?: 0
}