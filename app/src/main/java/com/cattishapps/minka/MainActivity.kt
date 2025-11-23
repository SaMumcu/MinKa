package com.cattishapps.minka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cattishapps.minka.ui.weekscreen.WeekScreen
import com.cattishapps.minka.ui.mainscreen.YearCalendar
import com.cattishapps.minka.ui.theme.MinKaTheme
import com.cattishapps.minka.util.Screen
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MinKaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).statusBarsPadding()
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.MonthScreen.route
                        ) {
                            composable(Screen.MonthScreen.route) {
                                YearCalendar(2025, onDayClick = { itemId ->
                                    navController.navigate(Screen.WeekScreen.createRoute(itemId))
                                })
                            }

                            composable(
                                route = Screen.WeekScreen.route,
                                arguments = listOf(navArgument("date") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val dateString = backStackEntry.arguments?.getString("date")
                                val date = dateString?.let { LocalDate.parse(it) }

                                if (date != null) {
                                    WeekScreen(
                                        navController = navController,
                                        selectedDate = date.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}