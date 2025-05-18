package com.example.travelplanapp.data.repository

import com.example.travelplanapp.data.dao.TicketInfoDao
import com.example.travelplanapp.data.model.TicketInfo
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 车票信息仓库
 * 
 * 作为数据源和ViewModel之间的中间层，提供统一的数据访问接口
 */
@Singleton
class TicketRepository @Inject constructor(
    private val ticketInfoDao: TicketInfoDao
) {
    /**
     * 获取所有车票信息
     * 
     * @return 所有车票信息的Flow
     */
    fun getAllTicketInfo(): Flow<List<TicketInfo>> {
        return ticketInfoDao.getAllTicketInfo()
    }
    
    /**
     * 获取未来的车票信息
     * 
     * @return 未来车票信息的Flow
     */
    fun getFutureTicketInfo(): Flow<List<TicketInfo>> {
        return ticketInfoDao.getFutureTicketInfo(System.currentTimeMillis())
    }
    
    /**
     * 根据ID获取车票信息
     * 
     * @param id 车票信息ID
     * @return 对应ID的车票信息Flow
     */
    fun getTicketInfoById(id: Long): Flow<TicketInfo> {
        return ticketInfoDao.getTicketInfoById(id)
    }
    
    /**
     * 保存车票信息
     * 
     * 如果ID为0，则插入新记录；否则更新现有记录
     * 
     * @param ticketInfo 要保存的车票信息
     * @return 插入的记录ID（如果是更新操作则返回原ID）
     */
    suspend fun saveTicketInfo(ticketInfo: TicketInfo): Long {
        return if (ticketInfo.id == 0L) {
            // 新记录，设置创建和更新时间
            val newTicket = ticketInfo.copy(
                createdAt = Date(),
                updatedAt = Date()
            )
            ticketInfoDao.insertTicketInfo(newTicket)
        } else {
            // 更新记录，只更新更新时间
            val updatedTicket = ticketInfo.copy(
                updatedAt = Date()
            )
            ticketInfoDao.updateTicketInfo(updatedTicket)
            ticketInfo.id
        }
    }
    
    /**
     * 删除车票信息
     * 
     * @param ticketInfo 要删除的车票信息
     */
    suspend fun deleteTicketInfo(ticketInfo: TicketInfo) {
        ticketInfoDao.deleteTicketInfo(ticketInfo)
    }
}
