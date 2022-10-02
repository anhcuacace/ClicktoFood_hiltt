package com.tunanh.clicktofood_hilt.ui.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.databinding.FragmentDetailBinding
import com.tunanh.clicktofood_hilt.ui.custemview.BottomSheetDialogFragment
import com.tunanh.clicktofood_hilt.ui.search.SearchFoodAdapter
import com.tunanh.clicktofood_hilt.util.shareLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.supervisorScope

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_detail
    override val viewModel: DetailViewModel by viewModels()

    private val adapter = SearchFoodAdapter()
    override fun initView() {
        val id = arguments?.getLong("idFood")
        id?.let { viewModel.loadInfo(it) }
        loadView()

    }

    private  fun loadView() {
        viewModel.SupervisorJob{

        }

        viewModel.foodInfo.observe(this) { food ->
            click(food)
            bindingData(food)
        }
        recyclerview()
    }

    private fun recyclerview() {
        binding.foodList.adapter = adapter
        viewModel.foodList.observe(this){
            adapter.addFoodList(it)
        }
        adapter.onClickItem = { it ->
            val food=Food(it.id,it.title,it.cost, star = it.star, img = it.img)
            val bottomSheetDialogFragment= BottomSheetDialogFragment(food = food)
            bottomSheetDialogFragment.show(childFragmentManager,"ActionButton")
        }
        adapter.onClickAdd={
            val food=Food(it.id,it.title,it.cost, star = it.star, img = it.img)
            mainViewModel.addToCard(food.also { food.amount+=1 })
            Toast.makeText(requireContext(), requireContext().getString(R.string.addfood), Toast.LENGTH_SHORT).show()
        }
        adapter.onClickLike={
            mainViewModel.updateLove(it)
            Toast.makeText(requireContext(), "added to favorites", Toast.LENGTH_SHORT).show()
        }
    }


    private fun bindingData(food: Food) {

            "   ${food.star}".also { binding.star.text = it }
            binding.foodName.text = food.title
            "${food.title}, ngon tuyệt zời".also { binding.foodName1.text = it }



    }

    private fun click(food: Food) {
        binding.actionBar.setOnClickImageLeft {
            getNavController().popBackStack()
        }
        binding.actionBar.setOnClickImageRight {
            food.img?.let { shareLink(it, requireContext()) }
        }
        binding.actionBar.setOnClickImageRight2 {
            getNavController().navigate(R.id.action_detailFragment_to_searchFragment)
        }
    }




}