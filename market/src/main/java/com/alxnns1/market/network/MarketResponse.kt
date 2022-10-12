package com.alxnns1.market.network

data class MarketResponse(
    val ShippingAds: List<ShippingAdResponse>,
    val BuyingAds: List<TradeAdResponse>,
    val SellingAds: List<TradeAdResponse>,
)

data class TradeAdResponse(
    val ContractNaturalId: Int,
    val PlanetNaturalId: String,
    val CreatorCompanyName: String,
    val MaterialTicker: String,
    val MaterialAmount: Int,
    val Price: Double,
    val PriceCurrency: String,
    val ExpiryTimeEpochMs: Long
)

data class ShippingAdResponse(
    val ContractNaturalId: Int,
    val PlanetNaturalId: String,
    val CreatorCompanyName: String,
    val OriginPlanetNaturalId: String,
    val OriginPlanetName: String,
    val DestinationPlanetNaturalId: String,
    val DestinationPlanetName: String,
    val CargoWeight: Double,
    val CargoVolume: Double,
    val PayoutPrice: Double,
    val PayoutCurrency: String,
    val ExpiryTimeEpochMs: Long
)