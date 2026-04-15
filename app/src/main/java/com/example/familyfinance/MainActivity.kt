package com.example.familyfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.familyfinance.presentation.FamilyFinanceApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as FamilyFinanceApplication).appContainer
        setContent {
            FamilyFinanceApp(appContainer = appContainer)
        }
    }
}
