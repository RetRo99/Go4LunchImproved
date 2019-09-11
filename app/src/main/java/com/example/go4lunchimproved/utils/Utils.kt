package com.example.go4lunchimproved.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadProfilePhoto(url: String?) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.loadPhotoFromUrl(photoReference: String?) {
    if (photoReference == null) {
        Glide.with(this)
            .load(ColorDrawable(Color.BLACK))
            .into(this)
    }   else{
        Glide.with(this)
            .load(photoReference)
            .placeholder(ColorDrawable(Color.BLACK))
            .into(this)
    }

}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun getCurrentDate():String{
    val sdf = SimpleDateFormat("YYYYMMDD")
    val currentDate = sdf.format(Date())
    return currentDate
}
