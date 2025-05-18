package com.example.travelplanapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 历史行程记录数据模型
 * 用于存储用户的历史行程信息
 */
@Entity(
    tableName = "trip_history",
    foreignKeys = [
        ForeignKey(
            entity = TicketInfo::class,
            parentColumns = ["id"],
            childColumns = ["ticketId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ticketId")]
)
data class TripHistory(
    /**
     * 行程记录ID
     */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * 关联的车票信息ID
     */
    val ticketId: Long,
    
    /**
     * 行程开始时间
     */
    val startTime: Date,
    
    /**
     * 行程结束时间
     */
    val endTime: Date,
    
    /**
     * 出发站点
     */
    val departureStation: String,
    
    /**
     * 到达站点
     */
    val arrivalStation: String,
    
    /**
     * 交通方式
     */
    val travelMode: String,
    
    /**
     * 预计用时（秒）
     */
    val estimatedDuration: Int,
    
    /**
     * 实际用时（秒）
     * 可为空，表示行程尚未完成
     */
    val actualDuration: Int? = null,
    
    /**
     * 是否准时
     * 可为空，表示行程尚未完成
     */
    val onTime: Boolean? = null,
    
    /**
     * 备注信息
     */
    val notes: String? = null
) {
    /**
     * 获取格式化的预计用时字符串
     * @return 格式化的时间字符串，如"1小时20分钟"
     */
    fun getFormattedEstimatedDuration(): String {
        val hours = estimatedDuration / 3600
        val minutes = (estimatedDuration % 3600) / 60
        
        return when {
            hours > 0 -> "$hours 小时 $minutes 分钟"
            else -> "$minutes 分钟"
        }
    }
    
    /**
     * 获取格式化的实际用时字符串
     * @return 格式化的时间字符串，如"1小时20分钟"，如果实际用时为空则返回"未完成"
     */
    fun getFormattedActualDuration(): String {
        return actualDuration?.let {
            val hours = it / 3600
            val minutes = (it % 3600) / 60
            
            when {
                hours > 0 -> "$hours 小时 $minutes 分钟"
                else -> "$minutes 分钟"
            }
        } ?: "未完成"
    }
    
    /**
     * 获取交通方式的本地化名称
     * @return 交通方式的本地化名称
     */
    fun getLocalizedTravelMode(): String {
        return when (travelMode) {
            "walking" -> "步行"
            "driving" -> "驾车"
            "transit" -> "公交"
            "riding" -> "骑行"
            else -> travelMode
        }
    }
    
    /**
     * 获取准时状态的本地化描述
     * @return 准时状态的本地化描述，如果准时状态为空则返回"未完成"
     */
    fun getOnTimeStatus(): String {
        return when (onTime) {
            true -> "准时"
            false -> "延误"
            null -> "未完成"
        }
    }
}
