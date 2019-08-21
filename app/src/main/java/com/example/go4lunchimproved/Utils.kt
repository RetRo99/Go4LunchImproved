package com.example.go4lunchimproved

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadProfilePhoto(url: Uri?){
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.loadPhotoFromUrl(photoReference:String){
    if(photoReference.isEmpty()){
        TODO("if empty url")
    }else{
        Glide.with(this)
            .load(photoReference)
            .placeholder(ColorDrawable(Color.BLACK))
            .into(this)
    }
    }