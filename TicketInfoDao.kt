package com.example.travelplanapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.travelplanapp.data.model.TicketInfo
import kotlinx.coroutines.flow.Flow

/**
 * 车票信息数据访问对象
 * 
 * 提供对车票信息表的CRUD操作
 */
@Dao
interface TicketInfoDao {
    /**
     * 插入新的车票信息
     * 
     * @param ticketInfo 要插入的车票信息对象
     * @return 插入后生成的ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicketInfo(ticketInfo: TicketInfo): Long
    
    /**
     * 更新车票信息
     * 
     * @param ticketInfo 要更新的车票信息对象
     */
    @Update
    suspend fun updateTicketInfo(ticketInfo: TicketInfo)
    
    /**
     * 删除车票信息
     * 
     * @param ticketInfo 要删除的车票信息对象
     */
    @Delete
    suspend fun deleteTicketInfo(ticketInfo: TicketInfo)
    
    /**
     * 根据ID获取车票信息
     * 
     * @param id 车票信息ID
     * @return 对应ID的车票信息对象
     */
    @Query("SELECT * FROM ticket_info WHERE id = :id")
    fun getTicketInfoById(id: Long): Flow<TicketInfo>
    
    /**
     * 获取所有车票信息，按发车时间降序排列
     * 
     * @return 所有车票信息的Flow
     */
    @Query("SELECT * FROM ticket_info ORDER BY departureTime DESC")
    fun getAllTicketInfo(): Flow<List<TicketInfo>>
    
    /**
     * 获取未来的车票信息，按发车时间升序排列
     * 
     * @param currentTime 当前时间
     * @return 未来车票信息的Flow
     */
    @Query("SELECT * FROM ticket_info WHERE departureTime > :currentTime ORDER BY departureTime ASC")
    fun getFutureTicketInfo(currentTime: Long): Flow<List<TicketInfo>>
}
