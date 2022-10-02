package com.tunanh.clicktofood_hilt.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tunanh.clicktofood_hilt.data.local.model.KeyWorkSearch
import com.tunanh.clicktofood_hilt.databinding.ItemHistoryBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener

class SearchHistoryAdapter : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {
    var onClickItem: ((KeyWorkSearch) -> Unit)? = null
    var onClickDeleteItem: ((KeyWorkSearch) -> Unit)? = null

    private var historyList: ArrayList<KeyWorkSearch>? = null

    class SearchHistoryViewHolder constructor(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeyWorkSearch) {
            binding.tvName.text = item.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val v =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(historyList!![position])
        holder.binding.tvName.setOnSingClickListener {
            onClickItem?.invoke(historyList!![position])
        }
        holder.binding.ivClear.setOnSingClickListener {
            onClickDeleteItem?.invoke(historyList!![position])
        }
    }

    override fun getItemCount(): Int = historyList?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun addHistoryList(historyList: ArrayList<KeyWorkSearch>) {
        this.historyList = historyList
        notifyDataSetChanged()
    }
}