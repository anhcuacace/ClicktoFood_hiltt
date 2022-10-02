package com.tunanh.clicktofood_hilt.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.activity.viewModels
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseActivity
import com.tunanh.clicktofood_hilt.databinding.ActivityMainBinding
import com.tunanh.clicktofood_hilt.util.hasNetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            viewModel.isNetworkConnection.value = hasNetworkConnection(context)
        }

    }

    override fun layoutRes(): Int = R.layout.activity_main

    public override val viewModel: MainViewModel by viewModels()

    override fun initView() {

    }



    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }


}