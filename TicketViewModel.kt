package com.example.travelplanapp.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelplanapp.data.model.TicketInfo
import com.example.travelplanapp.data.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/**
 * 车票信息ViewModel
 * 
 * 处理车票信息相关的业务逻辑，连接UI和数据层
 */
@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    // 当前编辑的车票信息
    private val _currentTicket = MutableStateFlow<TicketInfo?>(null)
    val currentTicket: StateFlow<TicketInfo?> = _currentTicket.asStateFlow()
    
    // 所有车票信息列表
    private val _allTickets = MutableStateFlow<List<TicketInfo>>(emptyList())
    val allTickets: StateFlow<List<TicketInfo>> = _allTickets.asStateFlow()
    
    // 操作状态
    private val _operationStatus = MutableLiveData<OperationStatus>()
    val operationStatus: LiveData<OperationStatus> = _operationStatus
    
    init {
        // 初始化时加载所有车票信息
        loadAllTickets()
    }
    
    /**
     * 加载所有车票信息
     */
    private fun loadAllTickets() {
        viewModelScope.launch {
            ticketRepository.getAllTicketInfo().collect { tickets ->
                _allTickets.value = tickets
            }
        }
    }
    
    /**
     * 加载指定ID的车票信息
     * 
     * @param ticketId 车票信息ID
     */
    fun loadTicket(ticketId: Long) {
        if (ticketId > 0) {
            viewModelScope.launch {
                ticketRepository.getTicketInfoById(ticketId).collect { ticket ->
                    _currentTicket.value = ticket
                }
            }
        } else {
            // 创建新车票，使用默认值
            _currentTicket.value = TicketInfo(
                departureTime = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000), // 默认为明天
                arrivalTime = Date(System.currentTimeMillis() + 26 * 60 * 60 * 1000),   // 默认为明天+2小时
                stationName = "",
                stationAddress = "",
                stationLatitude = 0.0,
                stationLongitude = 0.0,
                extraTime = 45 // 默认预留45分钟
            )
        }
    }
    
    /**
     * 保存车票信息
     * 
     * @param ticketInfo 要保存的车票信息
     */
    fun saveTicket(ticketInfo: TicketInfo) {
        viewModelScope.launch {
            try {
                val id = ticketRepository.saveTicketInfo(ticketInfo)
                _operationStatus.value = OperationStatus.Success(id)
                loadAllTickets() // 重新加载列表
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error(e.message ?: "保存失败")
            }
        }
    }
    
    /**
     * 删除车票信息
     * 
     * @param ticketInfo 要删除的车票信息
     */
    fun deleteTicket(ticketInfo: TicketInfo) {
        viewModelScope.launch {
            try {
                ticketRepository.deleteTicketInfo(ticketInfo)
                _operationStatus.value = OperationStatus.Success(ticketInfo.id, isDelete = true)
                loadAllTickets() // 重新加载列表
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error(e.message ?: "删除失败")
            }
        }
    }
    
    /**
     * 更新当前编辑的车票信息
     * 
     * @param updatedTicket 更新后的车票信息
     */
    fun updateCurrentTicket(updatedTicket: TicketInfo) {
        _currentTicket.value = updatedTicket
    }
    
    /**
     * 清除当前操作状态
     */
    fun clearOperationStatus() {
        _operationStatus.value = OperationStatus.Idle
    }
    
    /**
     * 操作状态密封类
     */
    sealed class OperationStatus {
        object Idle : OperationStatus()
        data class Success(val id: Long, val isDelete: Boolean = false) : OperationStatus()
        data class Error(val message: String) : OperationStatus()
    }
}
