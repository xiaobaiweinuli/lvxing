package com.example.travelplanapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * 车票信息数据模型
 * 
 * 用于存储用户输入的车票信息，包括发车时间、停车时间、车站位置等
 */
@Parcelize
@Entity(tableName = "ticket_info")
data class TicketInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // 车票基本信息
    val departureTime: Date,       // 发车时间
    val arrivalTime: Date,         // 停车时间
    val stationName: String,       // 车站名称
    val stationAddress: String,    // 车站详细地址
    
    // 地理位置信息
    val stationLatitude: Double,   // 车站纬度
    val stationLongitude: Double,  // 车站经度
    
    // 用户设置信息
    val transportationType: String = "", // 选择的交通方式
    val estimatedTravelTime: Int = 0,    // 预计交通用时（分钟）
    val extraTime: Int = 45,             // 预留空余时间（分钟）
    
    // 闹钟信息
    val departureAlarmTime: Date? = null,  // 出发闹钟时间
    val boardingAlarmTime: Date? = null,   // 上车闹钟时间
    val alightingAlarmTime: Date? = null,  // 下车闹钟时间
    
    // 创建和修改时间
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable
