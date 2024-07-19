package com.example.testapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.testapplication.datamodel.BannerDataModel
import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.screens.HorizontalPagerScreen
import com.example.testapplication.screens.SearchBoxScreen
import com.example.testapplication.screens.SetSearchList
import com.example.testapplication.ui.theme.TestApplicationTheme
import com.example.testapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    val bannerList by viewModel.bannerListFlow.collectAsState()
                    val searchList by viewModel._searchListFlowJetpack.collectAsState()
                    val isBottomSheetDataPresent by viewModel._isBottomSheetDataPresent.collectAsState()
                    val isShowBottomSheet by viewModel._isShowBottomSheet.collectAsState()
                    val bottomSheetMsg by viewModel._bottomSheetMessage.collectAsState()
                    val stateCollapsingToolbar = rememberCollapsingToolbarScaffoldState()

                    LoadMainScreen(
                        bannerList,
                        searchList.toList(),
                        isBottomSheetDataPresent,
                        isShowBottomSheet,
                        bottomSheetMsg,
                        stateCollapsingToolbar,
                        viewModel
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoadMainScreen(
    bannerList: List<BannerDataModel>,
    contentList: List<SearchDataModel>,
    isBottomSheetDataPresent: Boolean = false,
    isShowBottomSheet: Boolean = false,
    bottomSheetMsg: String = "",
    stateCollapsingToolbar: CollapsingToolbarScaffoldState,
    viewModel: MainActivityViewModel, modifier: Modifier = Modifier
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        contentColor = Color.White,
        //setting floating action button view
        floatingActionButton = {
            FloatingActionButton(
                content = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    viewModel.floatingActionButtonClick()
                    showBottomSheet = true
                },
                containerColor = colorResource(R.color.my_light_primary),
            )
        }
    ) { insets ->
        Column(modifier = modifier.padding(insets)) {
            //setting bottom sheet view
            if (isShowBottomSheet) {
                if (isBottomSheetDataPresent) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            viewModel._isShowBottomSheet.update { false }
                        },
                        sheetState = sheetState
                    ) {
                        Text(
                            text = bottomSheetMsg, fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                        )

                    }
                } else {
                    viewModel._isBottomSheetDataPresent.update { true }
                    viewModel._isShowBottomSheet.update { false }
                    Toast.makeText(
                        context,
                        stringResource(R.string.no_data_found), Toast.LENGTH_LONG
                    ).show()
                }
            }

            CollapsingToolbarScaffold(
                modifier = Modifier,
                state = stateCollapsingToolbar,
                scrollStrategy = ScrollStrategy.EnterAlways,
                toolbarModifier = Modifier
                    .fillMaxWidth(),

                toolbar = {
                    //setting Horizontal pager view
                    HorizontalPagerScreen(bannerList) {
                        viewModel._searchTextJetpack.value = TextFieldValue("")
                        viewModel.getSearchListing(it)

                    }
                }) {

                Column {
                    //setting search box view
                    SearchBoxScreen(state = viewModel._searchTextJetpack) {
                        viewModel.performFilterOperation(it)
                    }
                    //setting search list view
                    SetSearchList(contentList.toList())
                }
            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun LoadMainScreenPreview() {
//    TestApplicationTheme {
//        LoadMainScreen()
//    }
}