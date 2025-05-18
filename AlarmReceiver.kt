package com.example.travelplanapp.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.example.travelplanapp.R
import com.example.travelplanapp.TravelPlanApplication.Companion.CHANNEL_ALIGHTING_ID
import com.example.travelplanapp.TravelPlanApplication.Companion.CHANNEL_BOARDING_ID
import com.example.travelplanapp.TravelPlanApplication.Companion.CHANNEL_DEPARTURE_ID
import com.example.travelplanapp.data.model.TicketInfo
import com.example.travelplanapp.data.repository.TicketRepository
import com.example.travelplanapp.location.LocationService
import com.example.travelplanapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 闹钟接收器
 * 
 * 接收闹钟触发广播，显示通知并执行相应操作
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var ticketRepository: TicketRepository
    
    @Inject
    lateinit var locationService: LocationService
    
    // 协程作用域
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AlarmService.ALARM_ACTION) {
            val ticketId = intent.getLongExtra(AlarmService.EXTRA_TICKET_ID, -1)
            val alarmTypeStr = intent.getStringExtra(AlarmService.EXTRA_ALARM_TYPE) ?: return
            
            if (ticketId == -1L) return
            
            // 获取闹钟类型
            val alarmType = try {
                AlarmService.AlarmType.valueOf(alarmTypeStr)
            } catch (e: IllegalArgumentException) {
                return
            }
            
            // 处理闹钟触发
            handleAlarmTriggered(context, ticketId, alarmType)
        } else if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // 设备重启后恢复闹钟
            restoreAlarms()
        }
    }
    
    /**
     * 处理闹钟触发
     * 
     * @param context 上下文
     * @param ticketId 车票ID
     * @param alarmType 闹钟类型
     */
    private fun handleAlarmTriggered(context: Context, ticketId: Long, alarmType: AlarmService.AlarmType) {
        scope.launch {
            try {
                // 获取车票信息
                val ticket = ticketRepository.getTicketInfoById(ticketId).first()
                
                // 根据闹钟类型显示不同通知
                when (alarmType) {
                    AlarmService.AlarmType.DEPARTURE -> handleDepartureAlarm(context, ticket)
                    AlarmService.AlarmType.BOARDING -> handleBoardingAlarm(context, ticket)
                    AlarmService.AlarmType.ALIGHTING -> handleAlightingAlarm(context, ticket)
                }
            } catch (e: Exception) {
                // 处理异常
            }
        }
    }
    
    /**
     * 处理出发闹钟
     * 
     * @param context 上下文
     * @param ticket 车票信息
     */
    private fun handleDepartureAlarm(context: Context, ticket: TicketInfo) {
        scope.launch {
            // 获取当前位置
            val currentLocation = locationService.getLastLocation()
            
            if (currentLocation != null) {
                // 计算当前位置到初始位置的距离
                val distance = locationService.calculateDistance(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    ticket.stationLatitude,
                    ticket.stationLongitude
                )
                
                // 根据距离确定状态和提示内容
                val (title, content, color) = if (distance > 1000) {
                    Triple(
                        "请注意时间",
                        "距离${ticket.stationName}还有${String.format("%.1f", distance / 1000)}公里，请合理安排出发时间",
                        R.color.status_green
                    )
                } else {
                    Triple(
                        "请抓紧时间",
                        "距离${ticket.stationName}不到1公里，请抓紧时间前往",
                        R.color.status_red
                    )
                }
                
                // 显示通知
                showNotification(
                    context,
                    NOTIFICATION_ID_DEPARTURE,
                    CHANNEL_DEPARTURE_ID,
                    title,
                    content,
                    ticket.id,
                    color
                )
                
                // 触发震动
                vibrate(context)
            }
        }
    }
    
    /**
     * 处理上车闹钟
     * 
     * @param context 上下文
     * @param ticket 车票信息
     */
    private fun handleBoardingAlarm(context: Context, ticket: TicketInfo) {
        // 显示上车提醒通知
        showNotification(
            context,
            NOTIFICATION_ID_BOARDING,
            CHANNEL_BOARDING_ID,
            "请注意上车",
            "${ticket.stationName}即将发车，请注意上车",
            ticket.id,
            R.color.status_yellow
        )
        
        // 触发震动
        vibrate(context)
    }
    
    /**
     * 处理下车闹钟
     * 
     * @param context 上下文
     * @param ticket 车票信息
     */
    private fun handleAlightingAlarm(context: Context, ticket: TicketInfo) {
        // 显示下车提醒通知
        showNotification(
            context,
            NOTIFICATION_ID_ALIGHTING,
            CHANNEL_ALIGHTING_ID,
            "请注意下车",
            "即将到达目的地，请注意下车",
            ticket.id,
            R.color.status_black
        )
        
        // 触发震动
        vibrate(context)
    }
    
    /**
     * 显示通知
     * 
     * @param context 上下文
     * @param notificationId 通知ID
     * @param channelId 通知渠道ID
     * @param title 通知标题
     * @param content 通知内容
     * @param ticketId 车票ID
     * @param colorResId 状态颜色资源ID
     */
    private fun showNotification(
        context: Context,
        notificationId: Int,
        channelId: String,
        title: String,
        content: String,
        ticketId: Long,
        colorResId: Int
    ) {
        // 创建打开应用的Intent
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_TICKET_ID, ticketId)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // 获取默认铃声
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        
        // 创建通知
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(context.getColor(colorResId))
        
        // 显示通知
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
    
    /**
     * 触发震动
     * 
     * @param context 上下文
     */
    private fun vibrate(context: Context) {
        // 震动模式：0ms延迟，500ms震动，500ms暂停，500ms震动
        val pattern = longArrayOf(0, 500, 500, 500)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern, -1)
            }
        }
    }
    
    /**
     * 设备重启后恢复闹钟
     */
    private fun restoreAlarms() {
        scope.launch {
            try {
                // 获取未来的车票信息
                val tickets = ticketRepository.getFutureTicketInfo().first()
                
                // 重新设置闹钟
                val alarmService = AlarmService(context)
                
                for (ticket in tickets) {
                    ticket.departureAlarmTime?.let {
                        alarmService.setDepartureAlarm(ticket.id, it)
                    }
                    
                    ticket.boardingAlarmTime?.let {
                        alarmService.setBoardingAlarm(ticket.id, it)
                    }
                    
                    ticket.alightingAlarmTime?.let {
                        alarmService.setAlightingAlarm(ticket.id, it)
                    }
                }
            } catch (e: Exception) {
                // 处理异常
            }
        }
    }
    
    companion object {
        const val NOTIFICATION_ID_DEPARTURE = 1001
        const val NOTIFICATION_ID_BOARDING = 1002
        const val NOTIFICATION_ID_ALIGHTING = 1003
        const val EXTRA_TICKET_ID = "ticket_id"
    }
}
