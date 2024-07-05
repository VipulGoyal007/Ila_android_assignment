package com.example.testapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapplication.R

@Composable
fun ListViewItem(title: String, subTitle: String, imgId: Int,onClick: ()->Unit) {
   Card(  colors = CardDefaults.cardColors(
       containerColor = colorResource(R.color.tm_core_color_blue_90),
   ),modifier = Modifier.
   padding(8.dp).fillMaxWidth().
   clickable { onClick() }){
    Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "",
           modifier =  Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = subTitle, fontWeight = FontWeight.Normal, fontSize = 15.sp, color = Color.Black)
        }
    }}
}