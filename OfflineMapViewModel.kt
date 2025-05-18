package com.example.travelplanapp.ui.offline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelplanapp.data.preferences.UserPreferencesManager
import com.example.travelplanapp.map.OfflineMapArea
import com.example.travelplanapp.map.OfflineMapManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * 离线地图ViewModel
 * 处理离线地图相关的业务逻辑
 */
@HiltViewModel
class OfflineMapViewModel @Inject constructor(
    private val offlineMapManager: OfflineMapManager,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    // 离线地图区域列表
    private val _offlineMapAreas = MutableLiveData<List<OfflineMapArea>>()
    val offlineMapAreas: LiveData<List<OfflineMapArea>> = _offlineMapAreas
    
    // 总大小
    private val _totalSize = MutableLiveData<Long>()
    val totalSize: LiveData<Long> = _totalSize
    
    // 下载状态
    private val _downloadStatus = MutableLiveData<DownloadStatus?>()
    val downloadStatus: LiveData<DownloadStatus?> = _downloadStatus

    init {
        loadOfflineMapAreas()
    }

    /**
     * 加载离线地图区域列表
     */
    private fun loadOfflineMapAreas() {
        val areas = offlineMapManager.getDownloadedAreas()
        _offlineMapAreas.value = areas
        _totalSize.value = offlineMapManager.getTotalSize()
    }

    /**
     * 检查离线模式是否启用
     * @return 是否启用离线模式
     */
    fun isOfflineModeEnabled(): Boolean {
        return userPreferencesManager.getOfflineMode()
    }

    /**
     * 设置离线模式
     * @param enabled 是否启用
     */
    fun setOfflineMode(enabled: Boolean) {
        userPreferencesManager.setOfflineMode(enabled)
    }

    /**
     * 下载离线地图
     * @param areaName 区域名称
     * @param centerLocation 中心位置（格式：纬度,经度）
     * @param radiusKm 半径（公里）
     * @param provider 地图提供商
     */
    fun downloadOfflineMap(areaName: String, centerLocation: String, radiusKm: Int, provider: String) {
        viewModelScope.launch {
            try {
                _downloadStatus.value = DownloadStatus.Downloading(0)
                
                // 解析中心位置
                val parts = centerLocation.split(",")
                if (parts.size != 2) {
                    _downloadStatus.value = DownloadStatus.Error("位置格式错误，请使用'纬度,经度'格式")
                    return@launch
                }
                
                val latitude = parts[0].trim().toDoubleOrNull()
                val longitude = parts[1].trim().toDoubleOrNull()
                
                if (latitude == null || longitude == null) {
                    _downloadStatus.value = DownloadStatus.Error("位置格式错误，请使用有效的数字")
                    return@launch
                }
                
                // 计算边界
                val latDelta = radiusKm / 111.0  // 每1度纬度约111公里
                val lonDelta = radiusKm / (111.0 * Math.cos(Math.toRadians(latitude)))
                
                val boundNorth = latitude + latDelta
                val boundSouth = latitude - latDelta
                val boundEast = longitude + lonDelta
                val boundWest = longitude - lonDelta
                
                // 创建离线地图区域
                val area = OfflineMapArea(
                    id = UUID.randomUUID().toString(),
                    name = areaName,
                    size = (radiusKm * radiusKm * 0.1 * 1024 * 1024).toLong(), // 估算大小
                    downloadDate = System.currentTimeMillis(),
                    boundNorth = boundNorth,
                    boundSouth = boundSouth,
                    boundEast = boundEast,
                    boundWest = boundWest,
                    provider = provider
                )
                
                // 模拟下载进度
                for (i in 1..10) {
                    _downloadStatus.value = DownloadStatus.Downloading(i * 10)
                    kotlinx.coroutines.delay(300)
                }
                
                // 模拟下载地图数据
                val mapData = ByteArray(area.size.toInt()) { 0 }
                
                // 保存离线地图
                val success = offlineMapManager.downloadArea(area, mapData)
                
                if (success) {
                    _downloadStatus.value = DownloadStatus.Success("离线地图下载成功")
                    loadOfflineMapAreas()
                } else {
                    _downloadStatus.value = DownloadStatus.Error("离线地图下载失败")
                }
            } catch (e: Exception) {
                _downloadStatus.value = DownloadStatus.Error("下载失败: ${e.message}")
            }
        }
    }

    /**
     * 删除离线地图
     * @param areaId 区域ID
     */
    fun deleteOfflineMap(areaId: String) {
        viewModelScope.launch {
            val success = offlineMapManager.deleteArea(areaId)
            if (success) {
                loadOfflineMapAreas()
            }
        }
    }

    /**
     * 清除所有离线数据
     */
    fun clearAllOfflineData() {
        viewModelScope.launch {
            val success = offlineMapManager.clearAllData()
            if (success) {
                loadOfflineMapAreas()
            }
        }
    }

    /**
     * 下载状态密封类
     */
    sealed class DownloadStatus {
        data class Downloading(val progress: Int) : DownloadStatus()
        data class Success(val message: String) : DownloadStatus()
        data class Error(val message: String) : DownloadStatus()
    }
}
