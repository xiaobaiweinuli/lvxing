package com.example.travelplanapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

/**
 * 应用程序类
 * 
 * 用于初始化应用程序级别的组件，如Hilt依赖注入、通知渠道等
 */
@HiltAndroidApp
class TravelPlanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // 创建通知渠道
        createNotificationChannels()
    }
    
    /**
     * 创建应用所需的通知渠道
     * 
     * 在Android 8.0及以上版本，必须先创建通知渠道才能发送通知
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 出发提醒通知渠道
            val departureChannel = NotificationChannel(
                CHANNEL_DEPARTURE_ID,
                getString(R.string.channel_departure_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.channel_departure_description)
                enableVibration(true)
            }
            
            // 上车提醒通知渠道
            val boardingChannel = NotificationChannel(
                CHANNEL_BOARDING_ID,
                getString(R.string.channel_boarding_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.channel_boarding_description)
                enableVibration(true)
            }
            
            // 下车提醒通知渠道
            val alightingChannel = NotificationChannel(
                CHANNEL_ALIGHTING_ID,
                getString(R.string.channel_alighting_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.channel_alighting_description)
                enableVibration(true)
            }
            
            // 获取通知管理器并创建通知渠道
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannels(
                listOf(departureChannel, boardingChannel, alightingChannel)
            )
        }
    }
    
    companion object {
        // 通知渠道ID
        const val CHANNEL_DEPARTURE_ID = "departure_channel"
        const val CHANNEL_BOARDING_ID = "boarding_channel"
        const val CHANNEL_ALIGHTING_ID = "alighting_channel"
    }
}
