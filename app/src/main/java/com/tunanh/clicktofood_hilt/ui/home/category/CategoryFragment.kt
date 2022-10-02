package com.tunanh.clicktofood_hilt.ui.home.category

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.tunanh.clicktofood_hilt.ui.home.MainFragmentViewModel
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentCategoryBinding
import com.tunanh.clicktofood_hilt.ui.home.main.CategoryHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels()

    override fun initView() {
        val categoryHomeAdapter = CategoryHomeAdapter()
        val myViewModel: MainFragmentViewModel by viewModels()

        myViewModel.categoryList.observe(viewLifecycleOwner) { it1 ->
            categoryHomeAdapter.categoryList = it1.Categories
            binding.listCategory.adapter = categoryHomeAdapter
            categoryHomeAdapter.onClickItem = { category ->
                if (mainViewModel.isNetworkConnection.value == false) {
                    showDialogErrorNetwork()
                } else {
                    getNavController().navigate(
                        R.id.action_mainFragment_to_tempFragment,
                        bundleOf(
                            Pair("category", category.title)
                        )
                    )
                }
            }
        }

    }




}