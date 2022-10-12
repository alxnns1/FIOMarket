package com.alxnns1.market

interface MarketAd {
    val contractId: String
    val expiryTime: Long
    val timeRemaining: String
    val creatorCompany: String
    val price: String
    val currency: String
}

data class BuyAd(
    override val contractId: String,
    override val expiryTime: Long,
    override val timeRemaining: String,
    override val creatorCompany: String,
    override val price: String,
    override val currency: String,
    val material: String,
    val quantity: String
): MarketAd

data class SellAd(
    override val contractId: String,
    override val expiryTime: Long,
    override val timeRemaining: String,
    override val creatorCompany: String,
    override val price: String,
    override val currency: String,
    val material: String,
    val quantity: String
): MarketAd

data class ShippingAd(
    override val contractId: String,
    override val expiryTime: Long,
    override val timeRemaining: String,
    override val creatorCompany: String,
    override val price: String,
    override val currency: String,
    val weight: String,
    val volume: String,
    val origin: String,
    val destination: String
): MarketAd