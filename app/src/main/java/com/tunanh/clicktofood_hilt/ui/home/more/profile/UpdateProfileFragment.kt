package com.tunanh.clicktofood_hilt.ui.home.more.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.tunanh.clicktofood.ui.home.more.profile.UpdateProfileViewModel
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.data.local.model.User
import com.tunanh.clicktofood_hilt.databinding.FragmentUpdateProfileBinding
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import com.tunanh.clicktofood_hilt.util.showDialogSetting
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : BaseFragment<FragmentUpdateProfileBinding >() {
    override val viewModel: UpdateProfileViewModel by viewModels()
    private var count = 0
    private var mUri: Uri? = null
    private var mUser: User? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                if (count > 0) {
                    showDialogSetting(requireContext())
                } else {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.request_read_internal),
                        Toast.LENGTH_SHORT
                    ).show()
                    count += 1
                }
            }
        }
    private val mActivityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data ?: return@ActivityResultCallback
                    val uri = intent.data
                    if (uri != null) {
                        setUri(uri)
                    }
                    context?.let { Glide.with(it).load(uri).into(binding.avt) }
                }
            })

    override fun layoutRes(): Int = R.layout.fragment_update_profile




    override fun initView() {
        getInfo()
        updateInfo()
        click()
    }

    private fun click() {
        binding.actionBar.setOnClickImageLeft {
            getNavController().popBackStack()
        }

    }

    private fun getInfo() {
        viewModel.user.observe(this) {
            mUser = it
            context?.let { it1 ->
                Glide.with(it1).load(Uri.parse(mUser?.image)).error(R.drawable.ic_account_circle)
                    .into(binding.avt)
            }
            binding.name.setText(mUser?.name)
            binding.email.setText(mUser?.email)
            binding.phone.setText(mUser?.phone)
        }
    }

    private fun updateInfo() {

        binding.avt.setOnSingClickListener { onClickRequestRemission() }
        binding.btn.setOnSingClickListener { onClickUpdateProfile() }
    }

    private fun onClickUpdateProfile() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val strFullName: String = binding.name.text.toString().trim()
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(strFullName)
            .build()
        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Update profile success", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        mUser = User(
            email = binding.email.text.toString().trim(),
            name = strFullName,
            image = mUri.toString(),
            phone = binding.phone.text.toString().trim()
        )
        viewModel.updateUser(mUser!!)
        mainViewModel.isLoadProfile?.invoke()

    }

    private fun setUri(mUri: Uri) {
        this.mUri = mUri
    }

    private fun onClickRequestRemission() {


        //không có phiên bản android nhỏ hơn 6
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                openGallery()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }


    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }



}