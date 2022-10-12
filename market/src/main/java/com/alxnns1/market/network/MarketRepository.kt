package com.alxnns1.market.network

import com.alxnns1.market.BuyAd
import com.alxnns1.market.MarketAd
import com.alxnns1.market.SellAd
import com.alxnns1.market.ShippingAd
import com.alxnns1.utils.DateUtils
import com.alxnns1.utils.NumberFormatter
import com.alxnns1.utils.ResultOf
import javax.inject.Inject

class MarketRepository @Inject constructor(
    private val marketService: MarketService,
    private val dateUtils: DateUtils,
    private val numberFormatter: NumberFormatter
) {

    suspend fun getMarket(planet: String): ResultOf<List<MarketAd>> {
        return try {
            val marketResponseResult = marketService.getMarket(planet)
            if (marketResponseResult.isSuccessful && marketResponseResult.body() != null) {
                val body = marketResponseResult.body()!!
                ResultOf.Success(body.mapToMarketAds().sortedBy { it.expiryTime })
            } else {
                ResultOf.Error("No market found for $planet")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultOf.Error()
        }
    }

    private fun MarketResponse.mapToMarketAds(): List<MarketAd> {
        val buyAds = BuyingAds.map {
            BuyAd(
                contractId = "${it.PlanetNaturalId}/${it.ContractNaturalId}",
                expiryTime = it.ExpiryTimeEpochMs,
                timeRemaining = dateUtils.timeRemaining(it.ExpiryTimeEpochMs),
                creatorCompany = it.CreatorCompanyName,
                price = numberFormatter.format(it.Price),
                currency = it.PriceCurrency,
                material = it.MaterialTicker,
                quantity = numberFormatter.format(it.MaterialAmount)
            )
        }
        val sellAds = SellingAds.map {
            SellAd(
                contractId = "${it.PlanetNaturalId}/${it.ContractNaturalId}",
                expiryTime = it.ExpiryTimeEpochMs,
                timeRemaining = dateUtils.timeRemaining(it.ExpiryTimeEpochMs),
                creatorCompany = it.CreatorCompanyName,
                price = numberFormatter.format(it.Price),
                currency = it.PriceCurrency,
                material = it.MaterialTicker,
                quantity = numberFormatter.format(it.MaterialAmount)
            )
        }
        val shippingAds = ShippingAds.map {
            ShippingAd(
                contractId = "${it.PlanetNaturalId}/${it.ContractNaturalId}",
                expiryTime = it.ExpiryTimeEpochMs,
                timeRemaining = dateUtils.timeRemaining(it.ExpiryTimeEpochMs),
                creatorCompany = it.CreatorCompanyName,
                price = numberFormatter.format(it.PayoutPrice),
                currency = it.PayoutCurrency,
                weight = numberFormatter.format(it.CargoWeight),
                volume = numberFormatter.format(it.CargoVolume),
                origin = it.OriginPlanetName,
                destination = it.DestinationPlanetName
            )
        }

        return buyAds.plus(sellAds).plus(shippingAds)
    }
}