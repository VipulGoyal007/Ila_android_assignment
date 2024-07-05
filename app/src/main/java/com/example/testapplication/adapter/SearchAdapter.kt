package com.example.testapplication.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.databinding.SearchItemBinding
import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.viewmodel.MainActivityViewModel
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    var items: MutableList<SearchDataModel>? = null
    var viewmodel: MainActivityViewModel? = null

    constructor (
        items: MutableList<SearchDataModel>?,
        viewmodel: MainActivityViewModel?
    ) : this() {
        this.viewmodel = viewmodel
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.search_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder?.binding?.let {
            var currObj = items!![position]

            if (currObj != null) {
                it.tvSearchTitle.setText(currObj.title)
                it.tvSearchSubTitle.setText(currObj.subTitle)

                if (currObj.imageUrl != null)
                    it.ivSearchImg.setImageDrawable(currObj.imageUrl)
            }
            it.viewModel = viewmodel
            it.itemPosition = position

        }
    }


    override fun getItemCount(): Int = items!!.size

    class MyViewHolder(binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: SearchItemBinding

        init {
            this.binding = binding
        }
    }

}