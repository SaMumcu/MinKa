package com.cattishapps.minka.ui.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.cattishapps.minka.ui.Divider
import com.cattishapps.minka.ui.theme.Red
import com.cattishapps.minka.ui.theme.alfasLaboneFontFamily
import com.cattishapps.minka.util.MONTHS_IN_YEAR
import com.cattishapps.minka.util.Spacing
import com.cattishapps.minka.util.WEEK_DAYS
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun YearCalendar(year: Int, onDayClick: (LocalDate) -> Unit) {
    val selectedMonth: Int = LocalDate.now().monthValue
    val listState = rememberLazyListState()

    LaunchedEffect(selectedMonth) {
        listState.scrollToItem(selectedMonth - 1)
    }

    val months = (1..MONTHS_IN_YEAR).toList()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Spacing.medium)
    ) {
        items(months) { month ->
            CurrentMonth(year = year, currentMonth = month)
            MonthCalendar(year = year, month = month, onDayClick = onDayClick)
        }
    }
}

@Composable
fun CurrentMonth(
    year: Int,
    currentMonth: Int,
    modifier: Modifier = Modifier,
    today: LocalDate = LocalDate.now()
) {
    val monthName = Month.of(currentMonth).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

    val isThisMonth =
        (year == today.year && currentMonth == today.monthValue)

    Text(
        text = monthName,
        style = if (isThisMonth) {
            MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Red,
                fontFamily = alfasLaboneFontFamily
            )
        } else {
            MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = alfasLaboneFontFamily
            )
        },
        modifier = modifier.padding(horizontal = Spacing.medium),
        fontFamily = alfasLaboneFontFamily
    )
}

@Composable
fun MonthCalendar(
    year: Int,
    month: Int,
    today: LocalDate = LocalDate.now(),
    onDayClick: (LocalDate) -> Unit
) {

    Divider()
    HorizontalDaysInWeek()

    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()

    // Ayın 1. günü haftanın kaçıncı günü? (1 = Pazartesi)
    val firstDayOfWeek = LocalDate.of(year, month, 1).dayOfWeek.value
    // Kotlin ve java.time'da Pazartesi=1, Pazar=7

    // Haftanın 7 gününe göre boşluk sayısı (Pazartesi'den önce boş)
    val emptyCells = firstDayOfWeek - 1

    // Toplam hücre sayısı (boş + günler)
    val totalCells = emptyCells + daysInMonth

    // Satır sayısı (7 sütun)
    val rows = (totalCells + 6) / WEEK_DAYS

    Column {
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until WEEK_DAYS) {
                    val cellIndex = row * WEEK_DAYS + col
                    val dayNumber = cellIndex - emptyCells + 1
                    val isToday =
                        (year == today.year && month == today.monthValue && dayNumber == today.dayOfMonth)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable {
                                val date = LocalDate.of(year, month, dayNumber)
                                onDayClick(date)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (cellIndex in emptyCells..<totalCells) {
                            Text(
                                text = "${cellIndex - emptyCells + 1}",
                                style = if (isToday) {
                                    MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Red,
                                        fontFamily = alfasLaboneFontFamily
                                    )
                                } else {
                                    MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        fontFamily = alfasLaboneFontFamily
                                    )
                                },
                            )
                        } else {
                            Text(text = "")
                        }
                    }
                }
            }
        }
    }
    Divider()
}

@Composable
fun HorizontalDaysInWeek() {
    val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        daysOfWeek.forEach { day ->
            Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
    }
}