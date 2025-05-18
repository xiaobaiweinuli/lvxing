package com.example.travelplanapp.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelplanapp.data.model.Location
import com.example.travelplanapp.data.preferences.UserPreferencesManager
import com.example.travelplanapp.map.AMapService
import com.example.travelplanapp.map.BMapService
import com.example.travelplanapp.map.MapService
import com.example.travelplanapp.map.RouteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 路线计算测试ViewModel
 * 用于测试不同地图API和交通方式的路线计算
 */
@HiltViewModel
class RouteTestViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val aMapService: AMapService,
    private val bMapService: BMapService
) : ViewModel() {

    // 地图提供商
    private val _mapProvider = MutableLiveData<String>()
    val mapProvider: LiveData<String> = _mapProvider

    // 交通方式
    private val _travelMode = MutableLiveData<String>()
    val travelMode: LiveData<String> = _travelMode

    // 路线计算结果
    private val _routeResult = MutableLiveData<RouteResult?>()
    val routeResult: LiveData<RouteResult?> = _routeResult

    // 错误信息
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        viewModelScope.launch {
            _mapProvider.value = userPreferencesManager.mapProviderFlow.first()
            _travelMode.value = userPreferencesManager.defaultTravelModeFlow.first()
        }
    }

    /**
     * 设置地图提供商
     * @param provider 地图提供商名称 ("amap" 或 "bmap")
     */
    fun setMapProvider(provider: String) {
        _mapProvider.value = provider
    }

    /**
     * 设置交通方式
     * @param mode 交通方式 ("walking", "driving", "transit", "riding")
     */
    fun setTravelMode(mode: String) {
        _travelMode.value = mode
    }

    /**
     * 计算路线
     * @param originAddress 起点地址
     * @param destinationAddress 终点地址
     */
    suspend fun calculateRoute(originAddress: String, destinationAddress: String) {
        try {
            _errorMessage.value = null
            
            // 获取地图服务
            val mapService = getMapService()
            
            // 地理编码：地址转坐标
            val origin = mapService.geocode(originAddress)
            val destination = mapService.geocode(destinationAddress)
            
            if (origin == null || destination == null) {
                _errorMessage.value = "地址解析失败，请检查输入"
                return
            }
            
            // 计算路线
            val result = mapService.calculateRoute(
                origin = origin,
                destination = destination,
                travelMode = _travelMode.value ?: "driving"
            )
            
            _routeResult.value = result
        } catch (e: Exception) {
            _errorMessage.value = "路线计算失败: ${e.message}"
        }
    }

    /**
     * 计算最长用时路线
     * @param originAddress 起点地址
     * @param destinationAddress 终点地址
     */
    suspend fun calculateLongestRoute(originAddress: String, destinationAddress: String) {
        try {
            _errorMessage.value = null
            
            // 获取地图服务
            val mapService = getMapService()
            
            // 地理编码：地址转坐标
            val origin = mapService.geocode(originAddress)
            val destination = mapService.geocode(destinationAddress)
            
            if (origin == null || destination == null) {
                _errorMessage.value = "地址解析失败，请检查输入"
                return
            }
            
            // 计算最长用时路线
            val result = mapService.calculateLongestRoute(
                origin = origin,
                destination = destination
            )
            
            _routeResult.value = result
        } catch (e: Exception) {
            _errorMessage.value = "路线计算失败: ${e.message}"
        }
    }

    /**
     * 获取当前选择的地图服务
     */
    private suspend fun getMapService(): MapService {
        val provider = _mapProvider.value ?: "amap"
        
        return when (provider) {
            "amap" -> {
                val apiKey = userPreferencesManager.amapApiKeyFlow.first()
                if (apiKey.isEmpty()) {
                    throw IllegalStateException("高德地图API密钥未设置")
                }
                aMapService.initialize(apiKey)
                aMapService
            }
            "bmap" -> {
                val apiKey = userPreferencesManager.bmapApiKeyFlow.first()
                if (apiKey.isEmpty()) {
                    throw IllegalStateException("百度地图API密钥未设置")
                }
                bMapService.initialize(apiKey)
                bMapService
            }
            else -> throw IllegalStateException("不支持的地图提供商")
        }
    }
}
