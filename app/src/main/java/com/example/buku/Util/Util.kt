package com.example.buku.Util

import android.widget.ImageView
import com.example.buku.R
import com.squareup.picasso.Picasso

fun ImageView.setImageWithUrl(url: String?){
    if(url == null){
        this.setImageResource(R.drawable.icon_select_image)
    }
    else{
        Picasso.get().load(url).resize(240, 240).into(this)
    }
}