package com.tunanh.clicktofood_hilt.ui.placedorder

import androidx.fragment.app.viewModels
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentPlacedOrderBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacedOrderFragment : BaseFragment<FragmentPlacedOrderBinding >() {
    override fun layoutRes(): Int = R.layout.fragment_placed_order
    override val viewModel: PlacedOrderViewModel by viewModels()


    override fun initView() {
        mainViewModel.isLoadCart?.invoke()
        binding.goHome.setOnSingClickListener {
            getNavController().navigate(R.id.action_placedOrderFragment_to_mainFragment)
        }
    }


}