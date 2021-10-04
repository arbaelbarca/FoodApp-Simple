package com.arbaelbarca.foodapp.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.foodapp.R
import com.arbaelbarca.foodapp.datasource.remote.response.ResponseError
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.orhanobut.hawk.Hawk
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.HttpException
import java.io.IOException
import java.util.HashMap
import java.util.concurrent.TimeUnit

fun ImageView.loadImageUrl(url: String, context: Context) {
    Glide.with(context)
        .load(url)
//        .error(R.drawable.no_image)
//        .placeholder(R.drawable.no_image)
        .into(this)
}


fun hmsTimeFormatter(milliSeconds: Long): String? {
    return java.lang.String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(milliSeconds),
        TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(
                milliSeconds
            )
        ),
        TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                milliSeconds
            )
        )
    )
}

fun showView(view: View) {
    view.visibility = View.VISIBLE
}

//fun initHeaderAccesToken(): Map<String, String> {
//    val getUser = Hawk.get<ResponseLogin>(ConstVar.DATA_USER_LOGIN)
//    val map = HashMap<String, String>()
//    map["Authorization"] = "Bearer ${getUser.token}"
//    return map
//}

fun Toolbar.setToolbar(
    title: String, context: Context,
    appCompatActivity: AppCompatActivity
) {
    this.title = title
//    this.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    this.setTitleTextColor(ContextCompat.getColor(context, R.color.white))
    this.setNavigationOnClickListener {
        appCompatActivity.finish()
    }
}


fun hideView(view: View) {
    view.visibility = View.GONE
}

fun hideKeyboard(view: View, context: Context) {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.hideKeyboardNew(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

//fun initHeader(): Map<String, String> {
//    val getToken = Hawk.get<String>(Constant.ACCESS_TOKEN)
//    val map = HashMap<String, String>()
//    map["Authorization"] = "Bearer $getToken"
//    map["Content-Type"] = "application/json"
//    return map
//}
//
//fun getAccessToken(): String? {
//    return Hawk.get<String>(Constant.ACCESS_TOKEN)
//}


fun setRvAdapterVertikal(
    recyclerView: RecyclerView,
    adapterGroupieViewHolder: GroupAdapter<GroupieViewHolder>
) {
    recyclerView.apply {
        adapter = adapterGroupieViewHolder
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
    }
}


fun showToast(message: String?, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


fun setRvAdapterHorizontal(
    recyclerView: RecyclerView,
    adapterGroupieViewHolder: GroupAdapter<GroupieViewHolder>
) {
    recyclerView.apply {
        adapter = adapterGroupieViewHolder
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hasFixedSize()
    }
}

fun validateInput(editText: EditText): Boolean {
    var isEmpty = false
    val getText = editText.text.toString()
    if (getText.isEmpty()) {
        isEmpty = true
    }
    return isEmpty
}

fun intentPage(context: Context, classTarget: Class<*>) {
    val intent = Intent(context, classTarget)
    context.startActivity(intent)
}


fun intentPageData(context: Context, classTarget: Class<*>): Intent {
    return Intent(context, classTarget)
}

fun intentPageFlags(context: Context?, classTarget: Class<*>) {
    val intent = Intent(context, classTarget)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context?.startActivity(intent)
}


fun dexterPermissionMultiImage(context: Context) {
    Dexter.withContext(context)
        .withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {

            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>,
                token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        })
        .onSameThread()
        .check()
}

fun dexterPermissionMulti(context: Context) {
    Dexter.withContext(context)
        .withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {

            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>,
                token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        })
        .onSameThread()
        .check()
}

fun showAlertDialog(context: Context, message: String, callback: () -> Unit) {
    val alertDialog = AlertDialog.Builder(context)
    alertDialog.setMessage(message)
    alertDialog.setPositiveButton("Yes") { dialogInterface, i ->
        callback.invoke()
    }.setNegativeButton("No") { dialogInterface, i ->
        dialogInterface.dismiss()
    }

    alertDialog.create().show()
}

fun showErrorMessageThrowable(throwable: Throwable?): String {
    var message: String = ""
    if (throwable is HttpException) {
        val body = throwable.response()?.errorBody()
        val gson = Gson()
        val adapter: TypeAdapter<ResponseError> = gson.getAdapter(ResponseError::class.java)
        val errorResponse: ResponseError = adapter.fromJson(body?.string())
        message = errorResponse.message!!
        println("respon Message te $message")
    }
    return message
}