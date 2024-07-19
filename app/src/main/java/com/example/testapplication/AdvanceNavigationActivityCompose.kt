package com.example.testapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.testapplication.app.Navigation
import com.example.testapplication.datamodel.BannerDataModel
import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.screens.CategoryScreen
import com.example.testapplication.screens.DetailScreen
import com.example.testapplication.screens.HorizontalPagerScreen
import com.example.testapplication.screens.SearchBoxScreen
import com.example.testapplication.screens.SetSearchList
import com.example.testapplication.ui.theme.TestApplicationTheme
import com.example.testapplication.viewmodel.CategoryScreenActivityViewModel
import com.example.testapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@AndroidEntryPoint
class AdvanceNavigationActivityCompose : ComponentActivity() {
    private val viewModel: CategoryScreenActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var listData = listOf<String>(
                "Category 1", "Category 2", "Category 3",
                "Category 4", "Category 5", "Category 6", "Category 7", "Category 8",
                "Category 9", "Category 10", "Category 11",
            )
            //App3(listData)
           // App4(listData)
            App5(listData)

        }
    }
}


@Composable
fun App5(listData: List<String>) {
   
    Navigation(listData = listData)
  
}

@Composable
fun App3(listData: List<String>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "categoryscreen") {
        composable(route = "categoryscreen") {
            CategoryScreen(listData = listData, navController = navController)
            //LoginScreen(navController = navController)

        }
        composable(route = "detailscreen/{email}", arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
            }
        )) {
            val email = it.arguments?.getString("email") ?: ""
            DetailScreen(listData = listData, email= "")
//RegistrationScreen(navController = navController, value = email)
        }
    }
}

@Composable
fun App4(listData: List<String>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenA) {
        composable<ScreenA> {
            CategoryScreen(listData = listData, navController = navController)
        }
        composable<ScreenB> {
            val args = it.toRoute<ScreenB>()
            DetailScreen(listData = listData, email = args.email)

        }
    }


}

//new way of nav controller


@Serializable
data class ScreenB(val email: String, val tempData: String?=null)

@Serializable
object ScreenA

@Preview(showBackground = true)
@Composable
fun LoadMainScreenPreview3() {
//    TestApplicationTheme {
//        LoadMainScreen()
//    }
}