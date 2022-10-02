package com.tunanh.clicktofood_hilt.ui.home.more.wishlist

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tunanh.clicktofood_hilt.ui.custemview.BottomSheetDialogFragment
import com.tunanh.clicktofood_hilt.ui.temp.TempAdapter
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.FragmentWishListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment : BaseFragment<FragmentWishListBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_wish_list


    override val viewModel:  WishListViewModel by viewModels()
    override fun initView() {
        wishList()
        actionBar()
    }

    private fun actionBar() {
        binding.actionBar.setOnClickImageLeft {
            getNavController().popBackStack()
        }
    }

    private fun wishList() {
        val adapter = TempAdapter()
        viewModel.withList.observe(viewLifecycleOwner) {
            if (it == null || it.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                adapter.foodList = it as ArrayList<Food>
                binding.list.adapter = adapter
            }

        }
        adapter.onClickLike = { food, i ->
            mainViewModel.updateLove(food)
            Toast.makeText(requireContext(), "đã xóa ${food.title}", Toast.LENGTH_SHORT).show()
            adapter.foodList?.removeAt(i)
            adapter.notifyDataSetChanged()
            if (adapter.foodList?.isEmpty() == true) {
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }
        adapter.onClickItem = { it ->
            val bottomSheetDialogFragment = BottomSheetDialogFragment(food = it)
            bottomSheetDialogFragment.show(childFragmentManager, "ActionButton")
        }
        adapter.onClickAdd = { food ->
            mainViewModel.addToCard(food.also { food.amount += 1 })
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.addfood),
                Toast.LENGTH_SHORT
            ).show()
        }

    }



}