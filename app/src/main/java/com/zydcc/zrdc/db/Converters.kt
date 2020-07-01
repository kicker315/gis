package com.zydcc.zrdc.db

import androidx.room.TypeConverter
import java.util.*

/**
 * =======================================
 * Type converters to allow Room to reference complex data types
 * Create by ningsikai 2020/5/18:3:14 PM
 * ========================================
 */
class Converters {
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long =  calendar.timeInMillis

    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter fun dataToTimestamp(date: Date): Long {
        return date.time
    }
}