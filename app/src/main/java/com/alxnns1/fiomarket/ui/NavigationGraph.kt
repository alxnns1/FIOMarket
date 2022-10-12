package com.alxnns1.fiomarket.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.alxnns1.fiomarket.ui.theme.FIOMarketTheme
import com.alxnns1.market.ui.ARGUMENT_PLANET
import com.alxnns1.market.ui.MARKET_SCREEN_ROUTE
import com.alxnns1.market.ui.MarketScreen
import com.alxnns1.planets.ui.PLANET_SCREEN_ROUTE

@Composable
fun NavigationGraph(navController: NavHostController) {
    FIOMarketTheme {
        NavHost(navController = navController, startDestination = PLANET_SCREEN_ROUTE) {
            composable(PLANET_SCREEN_ROUTE) { com.alxnns1.planets.ui.PlanetsScreen { planet -> navController.navigate("$MARKET_SCREEN_ROUTE/$planet") } }
            composable(
                "$MARKET_SCREEN_ROUTE/{$ARGUMENT_PLANET}",
                arguments = listOf(navArgument(ARGUMENT_PLANET) { type = NavType.StringType })
            ) {
                MarketScreen(it.arguments?.getString(ARGUMENT_PLANET) ?: "")
            }
        }
    }
}