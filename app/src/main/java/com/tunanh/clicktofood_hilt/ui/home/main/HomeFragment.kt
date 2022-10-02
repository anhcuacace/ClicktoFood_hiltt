package com.tunanh.clicktofood.ui.home.main

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.tunanh.clicktofood_hilt.ui.home.MainFragmentViewModel
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentHomeBinding
import com.tunanh.clicktofood_hilt.ui.home.main.*
import com.tunanh.clicktofood_hilt.ui.main.MainActivity
import com.tunanh.clicktofood_hilt.util.openWebsite
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var handler = Handler(Looper.getMainLooper())
    private val adapterViewPager2 = ViewPagerAdapter()
    override fun layoutRes(): Int = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {


        (activity as MainActivity).showLoading()
        sliders()
        categories()
        foodList()
        onClick()
    }

    private fun onClick() {
        binding.seeAll.setOnSingClickListener {
            getNavController().navigate(
                R.id.action_mainFragment_to_tempFragment,
                bundleOf(Pair("category", "Beef"))
            )
        }
        binding.searchView.setOnSingClickListener {
            getNavController().navigate(
                R.id.action_mainFragment_to_searchFragment
            )
        }
    }

    private fun foodList() {
        val recyclerViewAdapter = RecyclerViewAdapter()
        val recommendAdapter = RecommendAdapter()
        viewModel.foodList.observe(this) {
            if (it == null || it.isEmpty()) {
                binding.rclv.visibility = View.GONE
            } else {
                binding.rclv.visibility = View.VISIBLE
                recommendAdapter.foodList = it
                recyclerViewAdapter.foodList = it

                binding.recyclerView2.adapter = recyclerViewAdapter
                binding.recyclerViewRecommend.adapter = recommendAdapter
            }

        }
        recommendAdapter.onClickLike = { food ->
            mainViewModel.updateLove(food)
            Toast.makeText(requireContext(), "added to favorites", Toast.LENGTH_SHORT).show()
        }
        recommendAdapter.onClickItem = {
            getNavController().navigate(
                R.id.action_mainFragment_to_detailFragment,
                bundleOf(Pair("idFood", it))
            )
        }
        recyclerViewAdapter.onClickItem = {
            getNavController().navigate(
                R.id.action_mainFragment_to_detailFragment,
                bundleOf(Pair("food", it))
            )
        }
    }

    private fun sliders() {


        viewModel.sliderList.observe(this) {
            adapterViewPager2.sliderList = it
            (activity as MainActivity).hiddenLoading()
            binding.viewPager2.adapter = adapterViewPager2
            adapterViewPager2.onClickItem = { sliders ->
                sliders.img?.let { it1 -> openWebsite(it1, requireContext()) }
            }
            binding.indicator.setViewPager(view?.findViewById(R.id.viewPager2))
            binding.viewPager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    handler.removeCallbacks(runnable())
                    if (position == 4) {

                        handler.postDelayed({
                            binding.viewPager2.currentItem = 0
                        }, 3000)
                    } else {
                        handler.postDelayed({
                            binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
                        }, 3000)
                    }

                }

            })
        }


    }

    private fun categories() {
        val adapter = CategoryHomeAdapter()
        val myViewModel: MainFragmentViewModel by viewModels()
        myViewModel.categoryList.observe(this) {
            adapter.categoryList = it.Categories
            binding.categoryes.adapter = adapter
        }
        adapter.onClickItem = { category ->
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


    override fun onResume() {
        super.onResume()
        handler.postDelayed(
            { binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1 },
            3000
        )
    }



}