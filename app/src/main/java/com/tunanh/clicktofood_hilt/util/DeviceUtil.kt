package com.tunanh.clicktofood_hilt.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ShareCompat
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.data.remote.model.Meal
import java.util.concurrent.ThreadLocalRandom


fun convertData(array: ArrayList<Meal>): List<Food> {
    val data = ArrayList<Food>()
    for (i in array.indices) {
        val food = Food(
            id = array[i].id!!,
            title = array[i].title.toString(),
            cost = ThreadLocalRandom.current().nextInt(20, 100),
            star = ThreadLocalRandom.current().nextDouble(3.5, 5.0),
            img = array[i].img
        )
        data.add(food)
    }
    return data
}


fun showDialogSetting(context: Context) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    context.startActivity(intent)
}

fun showKeyboard(mEtSearch: EditText, context: Context) {
    mEtSearch.requestFocus()
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(mEtSearch, 0)
}

fun hiddenSoftKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}

fun hasNetworkConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        )
            return true
    }
    return false
}

fun openWebsite(url: String, context: Context) {
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun openPlaystore(context: Context) {
    val appPackage = context.packageName
    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackage"))
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")
            )
        )
    }
}

fun shareApp(context: Context) {
    val appPackage = context.packageName
    ShareCompat.IntentBuilder(context)
        .setType("text/plain")
        .setChooserTitle("Chia sẻ qua...?")
        .setText("https://play.google.com/store/apps/details?id=$appPackage")
        .startChooser()
}

fun shareLink(link: String, context: Context) {
    ShareCompat.IntentBuilder(context)
        .setType("text/plain")
        .setChooserTitle("Chia sẻ qua...?")
        .setText(link)
        .startChooser()
}