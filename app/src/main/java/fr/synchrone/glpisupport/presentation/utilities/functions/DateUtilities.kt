package fr.synchrone.glpisupport.presentation.utilities.functions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtilities {

    val yyyyMMddDateFormat = run {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeZone = TimeZone.getTimeZone("UTC")
        dateFormat.timeZone = timeZone
        dateFormat
    }

    val ddMMyyyyDateFormat = run {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeZone = TimeZone.getTimeZone("UTC")
        dateFormat.timeZone = timeZone
        dateFormat
    }

    @SuppressLint("SimpleDateFormat")
    private val iso8061DateFormat =  run {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val timeZone = TimeZone.getTimeZone("UTC")
        dateFormat.timeZone = timeZone
        dateFormat
    }

    fun getISO8061StringFromDate(date: Date): String = iso8061DateFormat.format(date)

    fun getCalendarComponentFromDate(date: Date,
                                     component: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(component)
    }

    fun getDateFromCalendarComponents(year: Int = 2021,
                                      month: Int = 0,
                                      day: Int = 1): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

}

@SuppressLint("SimpleDateFormat")
fun Date.toyyyyMMddString(): String {
    return DateUtilities.yyyyMMddDateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toddMMyyyyString(): String {
    return DateUtilities.ddMMyyyyDateFormat.format(this)
}

fun String.toyyyyMMddDate(): Date? {
    return try {
        DateUtilities.yyyyMMddDateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun String.toddMMyyyyDate(): Date? {
    return try {
        DateUtilities.ddMMyyyyDateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}