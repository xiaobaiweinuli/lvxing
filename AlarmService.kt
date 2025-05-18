package com.example.travelplanapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 闹钟管理服务
 * 
 * 负责设置和管理出发、上车、下车等闹钟
 */
@Singleton
class AlarmService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // 闹钟管理器
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    /**
     * 设置出发闹钟
     * 
     * @param ticketId 车票ID
     * @param alarmTime 闹钟时间
     * @return 是否设置成功
     */
    fun setDepartureAlarm(ticketId: Long, alarmTime: Date): Boolean {
        return setAlarm(
            ticketId,
            alarmTime.time,
            AlarmType.DEPARTURE
        )
    }
    
    /**
     * 设置上车闹钟
     * 
     * @param ticketId 车票ID
     * @param alarmTime 闹钟时间
     * @return 是否设置成功
     */
    fun setBoardingAlarm(ticketId: Long, alarmTime: Date): Boolean {
        return setAlarm(
            ticketId,
            alarmTime.time,
            AlarmType.BOARDING
        )
    }
    
    /**
     * 设置下车闹钟
     * 
     * @param ticketId 车票ID
     * @param alarmTime 闹钟时间
     * @return 是否设置成功
     */
    fun setAlightingAlarm(ticketId: Long, alarmTime: Date): Boolean {
        return setAlarm(
            ticketId,
            alarmTime.time,
            AlarmType.ALIGHTING
        )
    }
    
    /**
     * 取消指定车票的所有闹钟
     * 
     * @param ticketId 车票ID
     */
    fun cancelAllAlarms(ticketId: Long) {
        cancelAlarm(ticketId, AlarmType.DEPARTURE)
        cancelAlarm(ticketId, AlarmType.BOARDING)
        cancelAlarm(ticketId, AlarmType.ALIGHTING)
    }
    
    /**
     * 设置闹钟
     * 
     * @param ticketId 车票ID
     * @param triggerTimeMillis 触发时间（毫秒）
     * @param alarmType 闹钟类型
     * @return 是否设置成功
     */
    private fun setAlarm(ticketId: Long, triggerTimeMillis: Long, alarmType: AlarmType): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ALARM_ACTION
            putExtra(EXTRA_TICKET_ID, ticketId)
            putExtra(EXTRA_ALARM_TYPE, alarmType.name)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            getRequestCode(ticketId, alarmType),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTimeMillis,
                        pendingIntent
                    )
                    true
                } else {
                    false
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
                true
            }
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 取消闹钟
     * 
     * @param ticketId 车票ID
     * @param alarmType 闹钟类型
     */
    private fun cancelAlarm(ticketId: Long, alarmType: AlarmType) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ALARM_ACTION
            putExtra(EXTRA_TICKET_ID, ticketId)
            putExtra(EXTRA_ALARM_TYPE, alarmType.name)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            getRequestCode(ticketId, alarmType),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
    
    /**
     * 获取请求码
     * 
     * 根据车票ID和闹钟类型生成唯一的请求码
     * 
     * @param ticketId 车票ID
     * @param alarmType 闹钟类型
     * @return 请求码
     */
    private fun getRequestCode(ticketId: Long, alarmType: AlarmType): Int {
        return when (alarmType) {
            AlarmType.DEPARTURE -> (ticketId * 10 + 1).toInt()
            AlarmType.BOARDING -> (ticketId * 10 + 2).toInt()
            AlarmType.ALIGHTING -> (ticketId * 10 + 3).toInt()
        }
    }
    
    companion object {
        const val ALARM_ACTION = "com.example.travelplanapp.ALARM"
        const val EXTRA_TICKET_ID = "ticket_id"
        const val EXTRA_ALARM_TYPE = "alarm_type"
    }
    
    /**
     * 闹钟类型枚举
     */
    enum class AlarmType {
        DEPARTURE,  // 出发闹钟
        BOARDING,   // 上车闹钟
        ALIGHTING   // 下车闹钟
    }
}
