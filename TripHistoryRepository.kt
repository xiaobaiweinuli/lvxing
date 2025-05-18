package com.example.travelplanapp.data.repository

import androidx.lifecycle.LiveData
import com.example.travelplanapp.data.dao.TripHistoryDao
import com.example.travelplanapp.data.model.TripHistory
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 历史行程记录仓库
 * 提供对历史行程记录的业务逻辑操作
 */
@Singleton
class TripHistoryRepository @Inject constructor(
    private val tripHistoryDao: TripHistoryDao
) {
    /**
     * 获取所有行程记录
     * @return 所有行程记录的LiveData
     */
    fun getAllTripHistories(): LiveData<List<TripHistory>> {
        return tripHistoryDao.getAllTripHistories()
    }
    
    /**
     * 获取未完成的行程记录
     * @return 未完成的行程记录列表
     */
    fun getUnfinishedTripHistories(): LiveData<List<TripHistory>> {
        return tripHistoryDao.getUnfinishedTripHistories()
    }
    
    /**
     * 根据ID获取行程记录
     * @param id 行程记录ID
     * @return 行程记录
     */
    suspend fun getTripHistoryById(id: Long): TripHistory? {
        return tripHistoryDao.getTripHistoryById(id)
    }
    
    /**
     * 获取指定时间范围内的行程记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 时间范围内的行程记录列表
     */
    suspend fun getTripHistoriesBetweenDates(startDate: Date, endDate: Date): List<TripHistory> {
        return tripHistoryDao.getTripHistoriesBetweenDates(startDate, endDate)
    }
    
    /**
     * 获取指定车票的行程记录
     * @param ticketId 车票ID
     * @return 指定车票的行程记录
     */
    suspend fun getTripHistoryByTicketId(ticketId: Long): TripHistory? {
        return tripHistoryDao.getTripHistoryByTicketId(ticketId)
    }
    
    /**
     * 插入一条行程记录
     * @param tripHistory 行程记录
     * @return 插入的记录ID
     */
    suspend fun insertTripHistory(tripHistory: TripHistory): Long {
        return tripHistoryDao.insert(tripHistory)
    }
    
    /**
     * 更新行程记录
     * @param tripHistory 行程记录
     */
    suspend fun updateTripHistory(tripHistory: TripHistory) {
        tripHistoryDao.update(tripHistory)
    }
    
    /**
     * 删除行程记录
     * @param tripHistory 行程记录
     */
    suspend fun deleteTripHistory(tripHistory: TripHistory) {
        tripHistoryDao.delete(tripHistory)
    }
    
    /**
     * 获取行程统计数据
     * @return 行程统计数据
     */
    suspend fun getTripStatistics(): TripStatistics {
        val totalTrips = tripHistoryDao.getTotalTripsCount()
        val onTimeTrips = tripHistoryDao.getOnTimeTripsCount()
        val delayedTrips = tripHistoryDao.getDelayedTripsCount()
        val averageDuration = tripHistoryDao.getAverageActualDuration() ?: 0.0
        val tripsByTravelMode = tripHistoryDao.getTripCountByTravelMode()
        val tripsByMonth = tripHistoryDao.getTripCountByMonth()
        
        return TripStatistics(
            totalTrips = totalTrips,
            onTimeTrips = onTimeTrips,
            delayedTrips = delayedTrips,
            averageDuration = averageDuration.toInt(),
            tripsByTravelMode = tripsByTravelMode,
            tripsByMonth = tripsByMonth
        )
    }
    
    /**
     * 删除所有行程记录
     */
    suspend fun deleteAllTripHistories() {
        tripHistoryDao.deleteAllTripHistories()
    }
    
    /**
     * 完成行程记录
     * @param tripId 行程记录ID
     * @param actualDuration 实际用时（秒）
     * @param onTime 是否准时
     */
    suspend fun completeTripHistory(tripId: Long, actualDuration: Int, onTime: Boolean) {
        val tripHistory = tripHistoryDao.getTripHistoryById(tripId) ?: return
        val updatedTrip = tripHistory.copy(
            actualDuration = actualDuration,
            onTime = onTime
        )
        tripHistoryDao.update(updatedTrip)
    }
}

/**
 * 行程统计数据类
 */
data class TripStatistics(
    val totalTrips: Int,
    val onTimeTrips: Int,
    val delayedTrips: Int,
    val averageDuration: Int,
    val tripsByTravelMode: Map<String, Int>,
    val tripsByMonth: Map<String, Int>
) {
    /**
     * 获取准时率
     * @return 准时率百分比
     */
    fun getOnTimeRate(): Float {
        return if (totalTrips > 0) {
            onTimeTrips.toFloat() / totalTrips * 100
        } else {
            0f
        }
    }
    
    /**
     * 获取延误率
     * @return 延误率百分比
     */
    fun getDelayRate(): Float {
        return if (totalTrips > 0) {
            delayedTrips.toFloat() / totalTrips * 100
        } else {
            0f
        }
    }
    
    /**
     * 获取未完成率
     * @return 未完成率百分比
     */
    fun getUnfinishedRate(): Float {
        val unfinishedTrips = totalTrips - onTimeTrips - delayedTrips
        return if (totalTrips > 0) {
            unfinishedTrips.toFloat() / totalTrips * 100
        } else {
            0f
        }
    }
    
    /**
     * 获取格式化的平均用时
     * @return 格式化的平均用时字符串
     */
    fun getFormattedAverageDuration(): String {
        val hours = averageDuration / 3600
        val minutes = (averageDuration % 3600) / 60
        
        return when {
            hours > 0 -> "$hours 小时 $minutes 分钟"
            else -> "$minutes 分钟"
        }
    }
}
