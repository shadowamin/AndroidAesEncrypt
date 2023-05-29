@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.aminprojects.androidencryptdycrept

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aminprojects.androidencryptdycrept.AesScreenType.AesDecryption
import com.aminprojects.androidencryptdycrept.AesScreenType.AesEncryption
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun AesPagerScreen() {
    val pagerState = rememberPagerState()
    val aesTypes = remember { listOf(AesEncryption, AesDecryption) }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "AES",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        TabLayout(tabTitles = aesTypes, pagerState = pagerState)
        TabContent(aesScreens = aesTypes, pagerState = pagerState)
    }
}

@Composable
fun TabLayout(tabTitles: List<AesScreenType>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    androidx.compose.material.TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {
            Spacer(modifier = Modifier.height(5.dp))
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(
                    pagerState = pagerState,
                    tabPositions
                ),
                height = 5.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.Transparent
    ) {
        tabTitles.forEachIndexed { index, type ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = type.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    }
}

@Composable
fun TabContent(
    aesScreens: List<AesScreenType>,
    pagerState: PagerState
) {
    val cryptManager = remember { AesEncryptManager() }
    HorizontalPager(state = pagerState, count = aesScreens.size) { index ->
        when (aesScreens[index]) {
            AesEncryption -> EncryptScreen(cryptManager = cryptManager)
            AesDecryption -> DecryptScreen(cryptManager = cryptManager)
        }
    }
}

@Preview
@Composable
fun DisplayAesPagerScreen() {
    AesPagerScreen()
}