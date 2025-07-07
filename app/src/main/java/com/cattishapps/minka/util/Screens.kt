package com.cattishapps.minka.util

import java.time.LocalDate

sealed class Screen(val route: String) {
    object MonthScreen : Screen("master")
    object DayScreen : Screen("detail/{date}") {
        fun createRoute(date: LocalDate) = "detail/${date.toString()}"
    }
}