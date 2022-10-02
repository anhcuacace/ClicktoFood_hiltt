package com.tunanh.clicktofood_hilt.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentSplashBinding
import com.tunanh.clicktofood_hilt.ui.main.MainActivity
import com.tunanh.clicktofood_hilt.util.hasNetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()


    override fun initView() {

        if (hasNetworkConnection(requireContext())) {
            transition()
        } else {
            binding.animationView.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                (activity as MainActivity).finish()
            }, 10000)
        }

    }

    private fun transition() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.intro.observe(this) {
                if (it) {
                    viewModel.user.observe(this) { it1 ->
                        if (it1) {
                            getNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                        } else {
                            getNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                        }
                    }

                } else {
                    getNavController().navigate(R.id.action_splashFragment_to_introFragment)
                }
            }
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        print("==== destroy")
    }




}