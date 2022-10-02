package com.tunanh.clicktofood_hilt.ui.login


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseFragment
import com.tunanh.clicktofood_hilt.databinding.FragmentLoginBinding
import com.tunanh.clicktofood_hilt.ui.main.MainActivity
import com.tunanh.clicktofood_hilt.util.setOnSingClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    companion object {
        private const val TAG = "Login"
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val timeLoading: Long = 3000


    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (Activity.RESULT_OK == result.resultCode) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    //google sign in was successful, authenticate with firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Timber.d("FirebaseAuthWithGoogle:" + account.id)

                    firebaseauthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // google sign in failed, update iu
                    Timber.w(e, "Google sign in failed")
                }
            }

        }

    override fun layoutRes(): Int = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        transparent()
        logInTransparent()
        loginGoogle()
        binding.btnLogIn.setOnSingClickListener {
            signInWithEmail()
        }
//        binding.zaloLogin.setOnSingClickListener {
//            signinWithZalo()
//        }
        signUpWithEmail()
    }


    //biến hóa
    private fun transparent() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.cardView.visibility = View.VISIBLE
            binding.animationView.visibility = View.GONE
        }, timeLoading)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun logInTransparent() {
        binding.tvSignUp.setOnClickListener {
            binding.tvSignUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            binding.tvSignUp.setTextColor(resources.getColor(R.color.white, null))
            binding.tvLogIn.background = null
            binding.signUpLayout.visibility = View.VISIBLE
            binding.logInLayout.visibility = View.GONE
            binding.tvLogIn.setTextColor(resources.getColor(R.color.a1, null))
            binding.btnLogIn.visibility = View.GONE
            binding.btnSignUp.visibility = View.VISIBLE
        }
        binding.tvLogIn.setOnClickListener {
            binding.tvLogIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            binding.tvLogIn.setTextColor(resources.getColor(R.color.white, null))
            binding.tvSignUp.background = null
            binding.logInLayout.visibility = View.VISIBLE
            binding.signUpLayout.visibility = View.GONE
            binding.tvSignUp.setTextColor(resources.getColor(R.color.a1, null))
            binding.btnSignUp.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE

        }

    }

    private fun loginGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))

            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
        binding.googleSignIn.setOnClickListener {
            signInGoogle()

        }
        auth = Firebase.auth
    }

    private fun firebaseauthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // sign in success, update UI with the signed- in user's information
                    Timber.d("signInwithcredential:success")
                    val user = auth.currentUser
                    (activity as MainActivity).hiddenLoading()
                    updateUI(user)
                } else {
                    //if sign in fails, display a message to the user
                    Timber.w(it.exception, "signinWithCredential:failure")
                    updateUI(null)
                }
            }
    }

    private fun signInGoogle() {
        (activity as MainActivity).showLoading()
        val signIntent = googleSignInClient.signInIntent
//        startActivityForResult(signIntent, RC_SING_IN)
        startForResult.launch(signIntent)
    }

    private fun signInWithEmail() {
        val username = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.tag(TAG).w(task.exception, "signInWithEmail:failure")
                    Toast.makeText(
                        context, "email or password fail",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun signUpWithEmail() {
        binding.btnSignUp.setOnClickListener {
            (activity as MainActivity).showLoading()
            val email: String = binding.edtEmailSignUp.text.toString().trim()
            val password = binding.edtPassSignUp.text.toString().trim()
            val confirmpass = binding.edtRePass.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmailSignUp.error = "Invaild email format"
                binding.edtEmailSignUp.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                binding.edtPassSignUp.error = "password can't be empty"
                binding.edtPassSignUp.requestFocus()
            } else if (password.length < 6) {
                binding.edtPassSignUp.error = "password must at least 6 charters long"
                binding.edtPassSignUp.requestFocus()
            } else if (password != confirmpass) {
                binding.edtRePass.error = "password is not matching"
                binding.edtRePass.requestFocus()
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val firebaseUser: FirebaseUser? = auth.currentUser

                    val emails = firebaseUser!!.email

                    Timber.d("create acc with $emails")
                    updateUI(firebaseUser)

                }.addOnFailureListener {
                    Timber.tag(TAG).d("sign up fail due to %s", it.message)
                    updateUI(null)
                }
            }
        }
    }

    // Login with zalo
//private fun signinWithZalo() {
//
//    ZaloSDK.Instance.authenticator()
//
//}
//    fun onLoginSuccess() {
//        updateUI(null)
//
//    }
//
//
//
//    fun onLoginError(code: Int, message: String) {
//        Toast.makeText(context, "[$code] $message", Toast.LENGTH_LONG).show()
//    }
//
//    private val listener = object : OAuthCompleteListener() {
//        override fun onGetOAuthComplete(response: OauthResponse?) {
//            if(TextUtils.isEmpty(response?.oauthCode)) {
//                onLoginError(response?.errorCode ?: -1, response?.errorMessage ?: "Unknown error")
//            } else {
//                onLoginSuccess()
//            }
//        }
//
//         fun onAuthenError(errorCode: Int, message: String?) {
//            onLoginError(errorCode, message ?: "Unknown error")
//        }
//    }

    private fun updateUI(user: FirebaseUser?) {
        (activity as MainActivity).hiddenLoading()
        if (user != null) {
            val img: String = if (user.photoUrl == null) {
                ""
            } else {
                user.photoUrl.toString()
            }

            viewModel.saveUser(
                user.email ?: "",
                user.displayName ?: "",
                img,
                user.phoneNumber ?: "",
                user.uid

            )
        }

        viewModel.loadDone = {
            getNavController().navigate(
                R.id.action_loginFragment_to_mainFragment
            )
        }
//
    }


}

