package com.example.testapplication.datamodel

import android.graphics.drawable.Drawable

data class BannerDataModel(val id:Int,val name:String,val sublist:ArrayList<String>,val imageUrl:Drawable?=null)