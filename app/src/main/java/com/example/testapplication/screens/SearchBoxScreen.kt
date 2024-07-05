package com.example.testapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapplication.R
import com.example.testapplication.viewmodel.MainActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBoxScreen(state: MutableState<TextFieldValue>,
                    onSearchTextChanged: (String) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentHeight().padding(16.dp)
            .background(Color.White)
    ) {
        OutlinedTextField(
            value = state.value,
             placeholder = { Text("Search here") },
            onValueChange = { value ->
                state.value = value
                onSearchTextChanged(value.text)
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    tint = colorResource(R.color.tm_core_color_blue_500),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                            onSearchTextChanged("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            tint = colorResource(R.color.tm_core_color_blue_500),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(1.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.tm_core_color_blue_500),
                unfocusedBorderColor = colorResource(R.color.tm_core_color_blue_500))

        )
    }
}