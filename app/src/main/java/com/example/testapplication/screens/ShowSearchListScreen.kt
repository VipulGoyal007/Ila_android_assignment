package com.example.testapplication.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testapplication.datamodel.SearchDataModel

@Composable
fun SetSearchList(
    dataList: List<SearchDataModel>,
    modifier: Modifier = Modifier
) {

    LazyColumn(content = {
        items(dataList) { item ->
            ListViewItem(
                item.title,
                item.subTitle,
                item.imageUrl!!, {}
            )
        }
    })

}