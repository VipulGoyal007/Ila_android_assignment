package com.example.testapplication.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.adapter.SearchAdapter
import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.viewmodel.MainActivityViewModel


@BindingAdapter(value = ["setSearchList", "viewmodel"])
fun setSearchList(
    view: RecyclerView,
    list: MutableList<SearchDataModel>,
    viewModel: MainActivityViewModel
) {
    view.adapter?.run {
        //notify
        if (this is SearchAdapter) {
            this.items = list
            this.viewmodel = viewModel
            this.notifyDataSetChanged()
        }
    } ?: run {
        view.adapter = SearchAdapter(list, viewModel)
    }
}
