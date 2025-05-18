package com.example.travelplanapp.util

import androidx.room.TypeConverter
import java.util.Date

/**
 * 日期转换器
 * 
 * 用于Room数据库中Date类型与Long类型的相互转换
 */
class DateConverter {
    /**
     * 将Date转换为Long类型（时间戳）
     * 
     * @param date 日期对象
     * @return 时间戳（毫秒）
     */
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
    
    /**
     * 将Long类型（时间戳）转换为Date
     * 
     * @param timestamp 时间戳（毫秒）
     * @return 日期对象
     */
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
