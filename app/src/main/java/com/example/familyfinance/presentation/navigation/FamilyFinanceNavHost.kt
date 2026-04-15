package com.example.familyfinance.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.familyfinance.presentation.screen.addpurchase.AddPurchaseScreen
import com.example.familyfinance.presentation.screen.addpurchase.AddPurchaseViewModel
import com.example.familyfinance.presentation.screen.groups.GroupsScreen
import com.example.familyfinance.presentation.screen.groups.GroupsViewModel
import com.example.familyfinance.presentation.screen.history.HistoryScreen
import com.example.familyfinance.presentation.screen.history.HistoryViewModel
import com.example.familyfinance.presentation.screen.home.HomeScreen
import com.example.familyfinance.presentation.screen.home.HomeViewModel
import com.example.familyfinance.presentation.screen.products.ProductsScreen
import com.example.familyfinance.presentation.screen.products.ProductsViewModel

@Composable
fun FamilyFinanceNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    factory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
        modifier = modifier
    ) {
        composable(NavRoutes.Home.route) {
            val vm: HomeViewModel = viewModel(factory = factory)
            HomeScreen(viewModel = vm, paddingValues = paddingValues)
        }
        composable(NavRoutes.Groups.route) {
            val vm: GroupsViewModel = viewModel(factory = factory)
            GroupsScreen(viewModel = vm, paddingValues = paddingValues)
        }
        composable(NavRoutes.Products.route) {
            val vm: ProductsViewModel = viewModel(factory = factory)
            ProductsScreen(viewModel = vm, paddingValues = paddingValues)
        }
        composable(NavRoutes.AddPurchase.route) {
            val vm: AddPurchaseViewModel = viewModel(factory = factory)
            AddPurchaseScreen(viewModel = vm, paddingValues = paddingValues)
        }
        composable(NavRoutes.History.route) {
            val vm: HistoryViewModel = viewModel(factory = factory)
            HistoryScreen(viewModel = vm, paddingValues = paddingValues)
        }
    }
}
