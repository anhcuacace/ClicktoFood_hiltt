package com.tunanh.clicktofood_hilt.ui.home.more

import android.net.Uri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.data.local.model.User
import com.tunanh.clicktofood_hilt.databinding.FragmentMoreBinding
import com.tunanh.clicktofood_hilt.listener.OnClickConfirmDialog
import com.tunanh.clicktofood_hilt.util.openPlaystore
import com.tunanh.clicktofood_hilt.util.openWebsite
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import com.tunanh.clicktofood_hilt.util.shareApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>() {
//    companion object {
//        fun newInstance() = MoreFragment()
//    }
    private var itemMoreList1 = mutableListOf<ItemMore>()
    private var itemMoreList2 = mutableListOf<ItemMore>()
    override val viewModel: MoreViewModel by viewModels ()

    private var user: User? = null
    override fun layoutRes(): Int = R.layout.fragment_more


    override fun initView() {
        getInfo()
        initRecycleView()

        editProfile()

    }
    private fun initRecycleView(){
        addAccItem()
        accountList()
        generalList()
    }

    private fun generalList() {
        val accountAdapter = AccountAdapter()


            accountAdapter.itemMore = itemMoreList2
            binding.generalList.adapter = accountAdapter

        accountAdapter.onClickItem = {
            when (it) {
                1 -> {
                    openPlaystore(requireContext())
                }
                2 -> {
                    showDialog("Thông báo",
                        "Tính năng đang phát triển",
                        "Đồng ý",
                        "",
                        object : OnClickConfirmDialog {
                            override fun onClickRightButton() {
                            }

                            override fun onClickLeftButton() {
                            }

                        })
                }
            }
        }

    }

    private fun editProfile() {
        binding.editProfile.setOnSingClickListener {
            getNavController().navigate(
                R.id.action_mainFragment_to_updateProfileFragment2
            )
        }
    }


    private fun getInfo() {
        viewModel.user.observe(viewLifecycleOwner) {
            user = it
            context?.let { it1 ->
                Glide.with(it1).load(Uri.parse(user?.image)).error(R.drawable.ic_account_circle)
                    .into(binding.avt)
            }
            binding.name.text = user?.name ?: "Name"
            binding.email.text = user?.email
            binding.phone.text = user?.phone ?: "Phone"
        }
        mainViewModel.isLoadProfile = {
            viewModel.getUser()
        }
    }

    private fun accountList() {

        val accountAdapter = AccountAdapter()

            accountAdapter.itemMore = itemMoreList1
            binding.accountList.adapter = accountAdapter

        accountAdapter.onClickItem = {
            when (it) {
                0 -> {
                    getNavController().navigate(
                        R.id.action_mainFragment_to_wishListFragment
                    )
                }
                3 -> {
                    openWebsite("https://messenger.com/t/100009599087926", requireContext())
                }
                6 -> {
                    shareApp(requireContext())
                }
                9 -> {
                    showDialog("Cảnh Báo",
                        "Bạn chắc chắn muốn đăng xuất",
                        "Đồng ý",
                        "Hủy",
                        object : OnClickConfirmDialog {
                            override fun onClickRightButton() {
                                logOut()
                            }

                            override fun onClickLeftButton() {
                            }

                        })
                }
                1, 2, 4, 5, 7, 8 -> {
                    showDialog("Thông báo",
                        "Tính năng đang phát triển",
                        "Đồng ý",
                        "",
                        object : OnClickConfirmDialog {
                            override fun onClickRightButton() {
                            }

                            override fun onClickLeftButton() {
                            }

                        })
                }
            }
        }
    }

    private fun logOut() {
        Firebase.auth.signOut()
        viewModel.logOut()
        getNavController().navigate(
            R.id.action_mainFragment_to_loginFragment
        )
    }
    private fun addAccItem() {

            addToList(R.drawable.ic_favorite, resources.getString(R.string.wishlist))
            addToList(R.drawable.percent, getString(R.string.promos))
            addToList(R.drawable.payments, getString(R.string.payment_methosds))
            addToList(R.drawable.ic_help, getString(R.string.help))
            addToList(R.drawable.language, resources.getString(R.string.language))
            addToList(R.drawable.ic_save, resources.getString(R.string.save))
            addToList(R.drawable.invite_frends, resources.getString(R.string.invite_friend))
            addToList(R.drawable.fingerprint, resources.getString(R.string.quick_login))
            addToList(R.drawable.notifications, resources.getString(R.string.notifications))
            addToList(R.drawable.logout, resources.getString(R.string.logout))

            addToList1(R.drawable.privacy, resources.getString(R.string.privacy))
            addToList1(R.drawable.rate, resources.getString(R.string.rate_app))


    }

    private fun addToList(img: Int, text: String) {
        val item = ItemMore(img, text)
        itemMoreList1.add(item)
    }

    private fun addToList1(img: Int, text: String) {
        val item = ItemMore(img, text)
        itemMoreList2.add(item)
    }





}