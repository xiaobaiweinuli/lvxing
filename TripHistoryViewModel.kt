package com.example.travelplanapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelplanapp.data.model.TripHistory
import com.example.travelplanapp.data.repository.TripHistoryRepository
import com.example.travelplanapp.data.repository.TripStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/**
 * 历史行程记录ViewModel
 * 处理历史行程记录相关的业务逻辑
 */
@HiltViewModel
class TripHistoryViewModel @Inject constructor(
    private val tripHistoryRepository: TripHistoryRepository
) : ViewModel() {

    // 所有行程记录
    val allTripHistories: LiveData<List<TripHistory>> = tripHistoryRepository.getAllTripHistories()
    
    // 未完成的行程记录
    val unfinishedTripHistories: LiveData<List<TripHistory>> = tripHistoryRepository.getUnfinishedTripHistories()
    
    // 当前选中的行程记录
    private val _selectedTripHistory = MutableLiveData<TripHistory?>()
    val selectedTripHistory: LiveData<TripHistory?> = _selectedTripHistory
    
    // 行程统计数据
    private val _tripStatistics = MutableLiveData<TripStatistics>()
    val tripStatistics: LiveData<TripStatistics> = _tripStatistics
    
    // 时间范围筛选
    private val _startDate = MutableLiveData<Date>()
    val startDate: LiveData<Date> = _startDate
    
    private val _endDate = MutableLiveData<Date>()
    val endDate: LiveData<Date> = _endDate
    
    // 筛选后的行程记录
    private val _filteredTripHistories = MutableLiveData<List<TripHistory>>()
    val filteredTripHistories: LiveData<List<TripHistory>> = _filteredTripHistories
    
    // 操作状态
    private val _operationStatus = MutableLiveData<OperationStatus>()
    val operationStatus: LiveData<OperationStatus> = _operationStatus

    init {
        // 默认时间范围为过去一个月
        val calendar = Calendar.getInstance()
        _endDate.value = calendar.time
        
        calendar.add(Calendar.MONTH, -1)
        _startDate.value = calendar.time
        
        // 加载统计数据
        loadTripStatistics()
    }

    /**
     * 加载行程统计数据
     */
    fun loadTripStatistics() {
        viewModelScope.launch {
            try {
                val statistics = tripHistoryRepository.getTripStatistics()
                _tripStatistics.value = statistics
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error("加载统计数据失败: ${e.message}")
            }
        }
    }

    /**
     * 根据时间范围筛选行程记录
     */
    fun filterTripHistoriesByDateRange() {
        viewModelScope.launch {
            try {
                val start = _startDate.value ?: return@launch
                val end = _endDate.value ?: return@launch
                
                val filteredTrips = tripHistoryRepository.getTripHistoriesBetweenDates(start, end)
                _filteredTripHistories.value = filteredTrips
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error("筛选行程记录失败: ${e.message}")
            }
        }
    }

    /**
     * 设置开始日期
     * @param date 开始日期
     */
    fun setStartDate(date: Date) {
        _startDate.value = date
        filterTripHistoriesByDateRange()
    }

    /**
     * 设置结束日期
     * @param date 结束日期
     */
    fun setEndDate(date: Date) {
        _endDate.value = date
        filterTripHistoriesByDateRange()
    }

    /**
     * 选择行程记录
     * @param tripHistory 行程记录
     */
    fun selectTripHistory(tripHistory: TripHistory) {
        _selectedTripHistory.value = tripHistory
    }

    /**
     * 清除选择的行程记录
     */
    fun clearSelectedTripHistory() {
        _selectedTripHistory.value = null
    }

    /**
     * 添加行程记录
     * @param tripHistory 行程记录
     */
    fun addTripHistory(tripHistory: TripHistory) {
        viewModelScope.launch {
            try {
                tripHistoryRepository.insertTripHistory(tripHistory)
                _operationStatus.value = OperationStatus.Success("行程记录添加成功")
                loadTripStatistics()
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error("行程记录添加失败: ${e.message}")
            }
        }
    }

    /**
     * 更新行程记录
     * @param tripHistory 行程记录
     */
    fun updateTripHistory(tripHistory: TripHistory) {
        viewModelScope.launch {
            try {
                tripHistoryRepository.updateTripHistory(tripHistory)
                _operationStatus.value = OperationStatus.Success("行程记录更新成功")
                loadTripStatistics()
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error("行程记录更新失败: ${e.message}")
            }
        }
    }

    /**
     * 删除行程记录
     * @param tripHistory 行程记录
     */
    fun deleteTripHistory(tripHistory: TripHistory) {
        viewModelScope.launch {
            try {
                tripHistoryRepository.deleteTripHistory(tripHistory)
                _operationStatus.value = OperationStatus.Success("行程记录删除成功")
                loadTripStatistics()
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error("行程记录删除失败: ${e.message}")
            }
        }
    }

    /**
     * 完成行程记录
     * @param tripId 行程记录ID
     * @param actualDuration 实际用时（秒）
     * @param onTime 是否准时
     */
    fun completeTripHistory(tripId: Long, actualDuration: Int, onTime: Boolean) {
        viewModelScope.launch {
            try {
                tripHistoryRepository.completeTripHistory(tripId, actualDuration, onTime)
                _operationStatus.value = OperationStatus.Success("行程记录完成状态更新成功")
                loadTripStatistics()
            } catch (e: Exception) {
                _operationStatus.value = OperationStatus.Error("行程记录完成状态更新失败: ${e.message}")
            }
        }
    }

    /**
     * 清除操作状态
     */
    fun clearOperationStatus() {
        _operationStatus.value = null
    }

    /**
     * 操作状态密封类
     */
    sealed class OperationStatus {
        data class Success(val message: String) : OperationStatus()
        data class Error(val message: String) : OperationStatus()
    }
}
