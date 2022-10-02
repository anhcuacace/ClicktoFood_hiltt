package com.tunanh.clicktofood_hilt.ui.intro

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentIntroBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>() {
    private val adapter = IntroAdapter()
    override fun layoutRes(): Int = R.layout.fragment_intro
    override val viewModel:  IntroViewModel by viewModels()


    override fun initView() {
        adapter.onItemClick = { _, _ ->
            //mở link nè
        }
        viewModel.introList.observe(this) {
            adapter.itemList = it
        }
        binding.screenViewpager.adapter = adapter

        binding.screenViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (binding.screenViewpager.currentItem == 2) {
                    changButton()
                } else {
                    binding.btn.text = resources.getText(R.string.next)
                    binding.btn.textSize = 20F
                    binding.btn.setOnSingClickListener {
                        binding.screenViewpager.apply {
                            beginFakeDrag()
                            fakeDragBy(-200f)
                            endFakeDrag()
                        }
                    }
                }
            }
        })
    }

    private fun changButton() {
        binding.btn.text = resources.getText(R.string.Getstarted)
        binding.btn.textSize = 14F
        binding.btn.setOnSingClickListener {
            viewModel.setIntro()
//            editor.putBoolean("intro",true)
//            editor.apply()
//            editor.commit()
            getNavController().navigate(
                R.id.action_introFragment_to_loginFragment
            )
        }
    }




}