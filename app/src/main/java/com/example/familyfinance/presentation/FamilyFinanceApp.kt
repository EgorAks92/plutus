package com.example.familyfinance.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.familyfinance.AppContainer
import com.example.familyfinance.presentation.navigation.BottomNavBar
import com.example.familyfinance.presentation.navigation.FamilyFinanceNavHost

@Composable
fun FamilyFinanceApp(
    appContainer: AppContainer,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val factory = AppViewModelFactory(appContainer)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    MaterialTheme {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                BottomNavBar(
                    currentRoute = navBackStackEntry?.destination?.route,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        ) { paddingValues ->
            FamilyFinanceNavHost(
                navController = navController,
                paddingValues = paddingValues,
                factory = factory
            )
        }
    }
}
