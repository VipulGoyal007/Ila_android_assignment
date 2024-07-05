package com.example.testapplication.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testapplication.R
import com.example.testapplication.datamodel.BannerDataModel
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
@ExperimentalFoundationApi
fun HorizontalPagerScreen(
    bannerList: List<BannerDataModel>,
    modifier: Modifier = Modifier,
    onPageChanged: (Int) -> Unit
) {


    if ( (bannerList?.size?:0)>0) {

        val pagerState = rememberPagerState(pageCount = {
            bannerList?.size ?: 0
        })


        LaunchedEffect(pagerState) {
            snapshotFlow {
                pagerState.currentPage
            }.distinctUntilChanged().collect { page ->
                onPageChanged(page)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column {
                HorizontalPager(
                    state = pagerState,
                ) { page ->

                    // Our page content
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { }) {
                        Image(
                            painter = painterResource(
                                bannerList?.get(page)?.imageUrl ?: R.drawable.fruits
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }


                }
                Spacer(modifier = Modifier.padding(2.dp))
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(12.dp)
                        )
                    }
                }
            }
        }

    }
}



