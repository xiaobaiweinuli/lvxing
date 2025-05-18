package com.example.travelplanapp.map

import com.example.travelplanapp.data.model.Location

/**
 * 地图服务接口
 * 定义了地图相关功能的抽象接口，支持不同地图提供商的实现
 */
interface MapService {
    /**
     * 初始化地图服务
     * @param apiKey 地图API密钥
     * @return 初始化是否成功
     */
    suspend fun initialize(apiKey: String): Boolean
    
    /**
     * 获取当前地图服务提供商名称
     * @return 地图服务提供商名称
     */
    fun getProviderName(): String
    
    /**
     * 计算两点间的路线
     * @param origin 起点位置
     * @param destination 终点位置
     * @param travelMode 交通方式 ("walking", "driving", "transit", "riding")
     * @return 路线计算结果
     */
    suspend fun calculateRoute(
        origin: Location,
        destination: Location,
        travelMode: String
    ): RouteResult
    
    /**
     * 计算多种交通方式的路线并返回用时最长的结果
     * @param origin 起点位置
     * @param destination 终点位置
     * @param travelModes 要计算的交通方式列表
     * @return 用时最长的路线计算结果
     */
    suspend fun calculateLongestRoute(
        origin: Location,
        destination: Location,
        travelModes: List<String> = listOf("walking", "driving", "transit", "riding")
    ): RouteResult {
        // 计算所有交通方式的路线
        val results = travelModes.map { mode ->
            calculateRoute(origin, destination, mode)
        }
        
        // 返回用时最长的路线
        return results.maxByOrNull { it.duration } ?: 
            RouteResult(0, 0, "", emptyList(), "unknown")
    }
    
    /**
     * 地理编码：地址转坐标
     * @param address 地址字符串
     * @return 位置坐标
     */
    suspend fun geocode(address: String): Location?
    
    /**
     * 反向地理编码：坐标转地址
     * @param location 位置坐标
     * @return 地址字符串
     */
    suspend fun reverseGeocode(location: Location): String?
    
    /**
     * 检查API密钥是否有效
     * @param apiKey 要检查的API密钥
     * @return 密钥是否有效
     */
    suspend fun validateApiKey(apiKey: String): Boolean
    
    /**
     * 设置是否使用离线地图
     * @param useOffline 是否使用离线地图
     */
    fun setOfflineMode(useOffline: Boolean)
    
    /**
     * 检查指定区域是否有离线地图数据
     * @param location 位置坐标
     * @return 是否有离线数据
     */
    fun hasOfflineData(location: Location): Boolean
}
