package com.alxnns1.market.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alxnns1.fiomarket.ui.theme.Buying
import com.alxnns1.fiomarket.ui.theme.Selling
import com.alxnns1.fiomarket.ui.theme.Shipping
import com.alxnns1.market.*
import com.alxnns1.utils.ResultOf
import com.alxnns1.utils.ui.ErrorLayout
import com.alxnns1.utils.ui.LoadingLayout

const val MARKET_SCREEN_ROUTE = "market_route"
const val ARGUMENT_PLANET = "planet"

@Composable
fun MarketScreen(planet: String) {
    val viewModel: MarketViewModel = hiltViewModel()
    val marketState by viewModel.marketState.collectAsState()

    Column {
        marketState.let {
            when (it) {
                is ResultOf.Success -> MarketSuccessLayout(it.value)
                is ResultOf.Error -> ErrorLayout(it.message)
                is ResultOf.Loading -> LoadingLayout()
                is ResultOf.Initial -> viewModel.getMarketAds(planet)
            }
        }
    }
}

@Composable
fun MarketSuccessLayout(marketAds: List<MarketAd>) {
    LazyColumn {
        items(items = marketAds) {
            when (it) {
                is BuyAd -> BuyAdLayout(it)
                is SellAd -> SellAdLayout(it)
                is ShippingAd -> ShippingAdLayout(it)
            }
        }
    }
}

@Composable
fun BuyAdLayout(ad: BuyAd) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Buying)
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = ad.contractId,
            style = TextStyle(fontSize = 12.sp)
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            text = "${ad.creatorCompany} is buying ${ad.quantity} ${ad.material}\nFor ${ad.price} ${ad.currency}"
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            text = "Expires in ${ad.timeRemaining}",
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun SellAdLayout(ad: SellAd) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Selling)
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = ad.contractId,
            style = TextStyle(fontSize = 12.sp)
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            text = "${ad.creatorCompany} is selling ${ad.quantity} ${ad.material}\nFor ${ad.price} ${ad.currency}"
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            text = "Expires in ${ad.timeRemaining}",
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun ShippingAdLayout(ad: ShippingAd) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Shipping)
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = ad.contractId,
            style = TextStyle(fontSize = 12.sp)
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            text = "${ad.creatorCompany} wants ${ad.weight}t / ${ad.volume}m³ shipped\nFrom ${ad.origin} to ${ad.destination}\nFor ${ad.price} ${ad.currency}"
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            text = "Expires in ${ad.timeRemaining}",
            style = TextStyle(fontSize = 12.sp)
        )
    }
}