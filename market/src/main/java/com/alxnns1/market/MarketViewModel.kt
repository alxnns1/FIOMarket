package com.alxnns1.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxnns1.market.MarketAd
import com.alxnns1.market.network.MarketRepository
import com.alxnns1.utils.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val marketRepository: MarketRepository
) : ViewModel() {

    private val _marketState = MutableStateFlow<ResultOf<List<MarketAd>>>(ResultOf.Initial)
    val marketState: StateFlow<ResultOf<List<MarketAd>>> = _marketState.asStateFlow()

    fun getMarketAds(planet: String) {
        viewModelScope.launch {
            _marketState.emit(ResultOf.Loading)
            _marketState.emit(marketRepository.getMarket(planet))
        }
    }
}