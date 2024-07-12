package com.example.testapplication.screens

import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapplication.R
import com.example.testapplication.viewmodel.CategoryScreenActivityViewModel
import com.example.testapplication.viewmodel.MainActivityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun CategoryScreen(navController: NavController, listData:List<String>) {
     //val viewModel: CategoryScreenActivityViewModel = viewModels()
    val viewModel = hiltViewModel<CategoryScreenActivityViewModel>()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
            items(listData.size )
            {
                CategoryItem(category = listData[it],navController)
            }
    }
}


@Composable
fun CategoryItem(category: String,navController: NavController) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, color = colorResource(R.color.tm_core_color_blue_90)),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Text(
            text = category,
            fontSize = 18.sp,
            color = Color.Green,
            modifier = Modifier
                .padding(16.dp).clickable {
                    navController.navigate("detailscreen/${category}")
                }

        )
    }
}