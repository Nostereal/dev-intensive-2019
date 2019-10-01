package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val SECONDS_IN_MINUTE = 60L
private const val MINUTES_IN_HOUR = 60L
private const val HOURS_IN_DAY = 24L
private const val DAYS_IN_YEAR = 365L


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormatter = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormatter.format(this)
}

fun Date.humanizeDiff(): String {
    /*
0с - 1с "только что"

1с - 45с "несколько секунд назад"

45с - 75с "минуту назад"

75с - 45мин "N минут назад"

45мин - 75мин "час назад"

75мин 22ч "N часов назад"

22ч - 26ч "день назад"

26ч - 360д "N дней назад"

>360д "более года назад"
     */
    val secondsDiff = (this.time - Date().time) / 1000L
    return when {
        secondsDiff <= 1 -> "только что"
        secondsDiff <= 45 -> "несколько секунд назад"
        secondsDiff <= 75 -> "минуту назад"
        secondsDiff <= 45 * SECONDS_IN_MINUTE -> "${secondsDiff / SECONDS_IN_MINUTE} минут назад"
        secondsDiff <= 75 * SECONDS_IN_MINUTE -> "час назад"
        secondsDiff <= 22 * MINUTES_IN_HOUR * SECONDS_IN_MINUTE -> "${secondsDiff / MINUTES_IN_HOUR / SECONDS_IN_MINUTE} часов назад"
        secondsDiff <= 26 * MINUTES_IN_HOUR * SECONDS_IN_MINUTE -> "день назад"
        secondsDiff <= 360 * DAYS_IN_YEAR * HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE -> "${secondsDiff / (DAYS_IN_YEAR * HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE)} дней назад"
        else -> "более года назад"
    }
}