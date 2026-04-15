package com.example.familyfinance.presentation.common

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun Double.asCurrency(): String = currencyFormatter.format(this)

fun Long.asDateString(): String = dateFormatter.format(Date(this))
