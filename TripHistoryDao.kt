package com.example.travelplanapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.travelplanapp.data.model.TripHistory
import java.util.Date

/**
 * 历史行程记录数据访问对象
 * 提供对历史行程记录的数据库操作
 */
@Dao
interface TripHistoryDao {
    /**
     * 插入一条行程记录
     * @param tripHistory 行程记录
     * @return 插入的记录ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tripHistory: TripHistory): Long
    
    /**
     * 更新行程记录
     * @param tripHistory 行程记录
     */
    @Update
    suspend fun update(tripHistory: TripHistory)
    
    /**
     * 删除行程记录
     * @param tripHistory 行程记录
     */
    @Delete
    suspend fun delete(tripHistory: TripHistory)
    
    /**
     * 获取所有行程记录
     * @return 所有行程记录的LiveData
     */
    @Query("SELECT * FROM trip_history ORDER BY startTime DESC")
    fun getAllTripHistories(): LiveData<List<TripHistory>>
    
    /**
     * 根据ID获取行程记录
     * @param id 行程记录ID
     * @return 行程记录
     */
    @Query("SELECT * FROM trip_history WHERE id = :id")
    suspend fun getTripHistoryById(id: Long): TripHistory?
    
    /**
     * 获取指定时间范围内的行程记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 时间范围内的行程记录列表
     */
    @Query("SELECT * FROM trip_history WHERE startTime BETWEEN :startDate AND :endDate ORDER BY startTime DESC")
    suspend fun getTripHistoriesBetweenDates(startDate: Date, endDate: Date): List<TripHistory>
    
    /**
     * 获取指定车票的行程记录
     * @param ticketId 车票ID
     * @return 指定车票的行程记录
     */
    @Query("SELECT * FROM trip_history WHERE ticketId = :ticketId")
    suspend fun getTripHistoryByTicketId(ticketId: Long): TripHistory?
    
    /**
     * 获取未完成的行程记录
     * @return 未完成的行程记录列表
     */
    @Query("SELECT * FROM trip_history WHERE actualDuration IS NULL ORDER BY startTime DESC")
    fun getUnfinishedTripHistories(): LiveData<List<TripHistory>>
    
    /**
     * 获取准时的行程记录数量
     * @return 准时的行程记录数量
     */
    @Query("SELECT COUNT(*) FROM trip_history WHERE onTime = 1")
    suspend fun getOnTimeTripsCount(): Int
    
    /**
     * 获取延误的行程记录数量
     * @return 延误的行程记录数量
     */
    @Query("SELECT COUNT(*) FROM trip_history WHERE onTime = 0")
    suspend fun getDelayedTripsCount(): Int
    
    /**
     * 获取总行程数量
     * @return 总行程数量
     */
    @Query("SELECT COUNT(*) FROM trip_history")
    suspend fun getTotalTripsCount(): Int
    
    /**
     * 获取平均实际用时
     * @return 平均实际用时（秒）
     */
    @Query("SELECT AVG(actualDuration) FROM trip_history WHERE actualDuration IS NOT NULL")
    suspend fun getAverageActualDuration(): Double?
    
    /**
     * 获取按交通方式分组的行程数量
     * @return 交通方式和对应的行程数量
     */
    @Query("SELECT travelMode, COUNT(*) as count FROM trip_history GROUP BY travelMode")
    suspend fun getTripCountByTravelMode(): Map<String, Int>
    
    /**
     * 获取按月份分组的行程数量
     * @return 月份和对应的行程数量
     */
    @Query("SELECT strftime('%Y-%m', startTime / 1000, 'unixepoch') as month, COUNT(*) as count FROM trip_history GROUP BY month ORDER BY month")
    suspend fun getTripCountByMonth(): Map<String, Int>
    
    /**
     * 删除所有行程记录
     */
    @Query("DELETE FROM trip_history")
    suspend fun deleteAllTripHistories()
}
