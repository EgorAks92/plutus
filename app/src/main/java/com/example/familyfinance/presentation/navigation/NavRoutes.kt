package com.example.familyfinance.presentation.navigation

sealed class NavRoutes(val route: String, val label: String) {
    data object Home : NavRoutes("home", "Home")
    data object Groups : NavRoutes("groups", "Groups")
    data object Products : NavRoutes("products", "Products")
    data object AddPurchase : NavRoutes("add_purchase", "Add")
    data object History : NavRoutes("history", "History")
}

val bottomNavRoutes = listOf(
    NavRoutes.Home,
    NavRoutes.Groups,
    NavRoutes.Products,
    NavRoutes.AddPurchase,
    NavRoutes.History
)
