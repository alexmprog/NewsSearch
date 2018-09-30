package com.renovavision.newssearch.utils

import android.content.Context
import android.text.TextUtils
import com.renovavision.newssearch.R
import org.joda.time.*
import java.util.*

object DateTimeUtils {

    private val YEAR_TIME = "MMMM dd, YYYY"

    fun getLocalDateTime(dateText: String?): DateTime {
        return try {
            val currentUTC = DateTime(dateText, DateTimeZone.UTC)
            val localTimeZone = TimeZone.getDefault()
            DateTime(currentUTC, DateTimeZone.forID(localTimeZone.id))
        } catch (e: Exception) {
            // default current date
            DateTime.now()
        }

    }

    fun getHappenedTime(startPeriod: DateTime, context: Context): String {
        val endPeriod = DateTime.now()

        // check years
        val years = Years.yearsBetween(startPeriod, endPeriod).years
        if (years >= 1) {
            val value = context.getString(R.string.ago_years)
            return String.format(Locale.ENGLISH, value, years)
        }

        // check months and weeks
        val months = Months.monthsBetween(startPeriod, endPeriod).months
        if (months >= 1) {
            val weeks = Weeks.weeksBetween(startPeriod, endPeriod).weeks
            val value = context.getString(R.string.ago_weeks)
            return String.format(Locale.ENGLISH, value, weeks)
        }

        // check days
        val days = Days.daysBetween(startPeriod, endPeriod).days
        if (days >= 1) {
            val value = context.getString(R.string.ago_days)
            return String.format(Locale.ENGLISH, value, days)
        }

        // check hours
        val hours = Hours.hoursBetween(startPeriod, endPeriod).hours
        if (hours >= 1) {
            val value = context.getString(R.string.ago_hours)
            return String.format(Locale.ENGLISH, value, hours)
        }

        // check minutes
        val minutes = Minutes.minutesBetween(startPeriod, endPeriod).minutes
        if (minutes >= 1) {
            val value = context.getString(R.string.ago_minutes)
            return String.format(Locale.ENGLISH, value, minutes)
        }

        // check seconds
        var seconds = Seconds.secondsBetween(startPeriod, endPeriod).seconds
        if (seconds == 0) {
            seconds = 1
        }
        val value = context.getString(R.string.ago_seconds)
        return String.format(Locale.ENGLISH, value, seconds)
    }

    fun getYearTime(dateTime: DateTime): String? {
        return dateTime.toString(YEAR_TIME, Locale.US)
    }

}