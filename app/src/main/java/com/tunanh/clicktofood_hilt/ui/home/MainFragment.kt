package com.tunanh.clicktofood_hilt.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tunanh.clicktofood.ui.home.main.HomeFragment
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentMainBinding
import com.tunanh.clicktofood_hilt.ui.home.cart.CartFragment
import com.tunanh.clicktofood_hilt.ui.home.category.CategoryFragment
import com.tunanh.clicktofood_hilt.ui.home.more.MoreFragment


class MainFragment : BaseFragment<FragmentMainBinding>() {


    override fun layoutRes(): Int = R.layout.fragment_main

    override val viewModel: MainFragmentViewModel by viewModels()

    override fun initView() {
        val fragmentList =
            arrayListOf<Fragment>(HomeFragment(), CategoryFragment(), CartFragment(), MoreFragment())
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager2.isUserInputEnabled=false
        binding.viewPager2.adapter=adapter

        binding.buttonNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home->{
                    binding.viewPager2.currentItem=0
                    return@setOnItemSelectedListener true
                }
                R.id.category->{
                    binding.viewPager2.currentItem=1
                    return@setOnItemSelectedListener true
                }
                R.id.cart->{
                    binding.viewPager2.currentItem=2
                    return@setOnItemSelectedListener true
                }
                R.id.more->{
                    binding.viewPager2.currentItem=3
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }

    }




}