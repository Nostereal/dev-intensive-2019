package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND = 1000L
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormatter = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormatter.format(this)
}

fun Date.add(value: Int, units: String): Date {
    var time = this.time
    time += when (units) {
        "second", "seconds" -> value * SECOND
        "minute", "minutes" -> value * MINUTE
        "hour", "hours" -> value * HOUR
        "day", "days" -> value * DAY
        else -> throw IllegalStateException("invalid units")
    }

    this.time += time
    return this
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
    val msDiff = this.time - Date().time
    return when {
        msDiff <= 1 * SECOND -> "только что"
        msDiff <= 45 * SECOND -> "несколько секунд назад"
        msDiff <= 75 * SECOND -> "минуту назад"
        msDiff <= 45 * MINUTE -> "${msDiff / MINUTE} минут назад"
        msDiff <= 75 * MINUTE -> "час назад"
        msDiff <= 22 * HOUR -> "${msDiff / HOUR} часов назад"
        msDiff <= 26 * HOUR -> "день назад"
        msDiff <= 360 * DAY -> "${msDiff / DAY} дней назад"
        else -> "более года назад"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun TimeUnits.plural(value: Int): String {
    var plural = "$value "
    when (this) {
        TimeUnits.SECOND -> plural += when {
            value % 100 in 11..14 -> "секунд"
            value % 10 in 2..4 -> "секунды"
            value % 10 == 1 -> "секунду"
            else -> "секунд"
        }
        TimeUnits.MINUTE -> plural += when {
            value % 100 in 11..14 -> "минут"
            value % 10 in 2..4 -> "минуты"
            value % 10 == 1 -> "минуту"
            else -> "минут"
        }
        TimeUnits.HOUR -> plural += when {
            value % 100 in 11..14 -> "часов"
            value % 10  == 1 -> "час"
            value % 10 in 2..4 -> "часа"
            else -> " часов"
        }
        TimeUnits.DAY -> plural += when {
            value % 100 in 11..14 -> "дней"
            value % 10 in 2..4 -> "дня"
            value % 10 == 1 -> "день"
            else -> "дней"
        }
    }
    return plural
}