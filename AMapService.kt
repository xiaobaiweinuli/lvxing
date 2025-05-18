package com.example.travelplanapp.map

import android.util.Log
import com.example.travelplanapp.data.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 高德地图服务实现类
 * 实现MapService接口，提供高德地图相关功能
 */
@Singleton
class AMapService @Inject constructor() : MapService {
    
    private val TAG = "AMapService"
    private var apiKey: String = ""
    private var isOfflineMode: Boolean = false
    
    /**
     * 初始化高德地图服务
     * @param apiKey 高德地图API密钥
     * @return 初始化是否成功
     */
    override suspend fun initialize(apiKey: String): Boolean {
        return try {
            this.apiKey = apiKey
            // 验证API密钥是否有效
            val isValid = validateApiKey(apiKey)
            if (isValid) {
                Log.d(TAG, "高德地图API初始化成功")
            } else {
                Log.e(TAG, "高德地图API密钥无效")
            }
            isValid
        } catch (e: Exception) {
            Log.e(TAG, "高德地图API初始化失败: ${e.message}")
            false
        }
    }
    
    /**
     * 获取当前地图服务提供商名称
     * @return 地图服务提供商名称
     */
    override fun getProviderName(): String = "高德地图"
    
    /**
     * 计算两点间的路线
     * @param origin 起点位置
     * @param destination 终点位置
     * @param travelMode 交通方式 ("walking", "driving", "transit", "riding")
     * @return 路线计算结果
     */
    override suspend fun calculateRoute(
        origin: Location,
        destination: Location,
        travelMode: String
    ): RouteResult = withContext(Dispatchers.IO) {
        try {
            if (isOfflineMode && hasOfflineData(origin) && hasOfflineData(destination)) {
                // 使用离线数据计算路线
                calculateOfflineRoute(origin, destination, travelMode)
            } else {
                // 使用在线API计算路线
                calculateOnlineRoute(origin, destination, travelMode)
            }
        } catch (e: Exception) {
            Log.e(TAG, "路线计算失败: ${e.message}")
            RouteResult(0, 0, "路线计算失败", emptyList(), travelMode)
        }
    }
    
    /**
     * 使用在线API计算路线
     */
    private suspend fun calculateOnlineRoute(
        origin: Location,
        destination: Location,
        travelMode: String
    ): RouteResult = withContext(Dispatchers.IO) {
        // 根据交通方式选择对应的API
        val apiType = when (travelMode) {
            "walking" -> "walking"
            "driving" -> "driving"
            "transit" -> "transit"
            "riding" -> "bicycling"
            else -> "driving"
        }
        
        // 构建API请求URL
        val urlString = "https://restapi.amap.com/v3/direction/$apiType?" +
                "origin=${origin.longitude},${origin.latitude}" +
                "&destination=${destination.longitude},${destination.latitude}" +
                "&output=json" +
                "&key=$apiKey"
        
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        
        try {
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                parseRouteResponse(response, travelMode)
            } else {
                Log.e(TAG, "API请求失败，响应码: $responseCode")
                RouteResult(0, 0, "路线计算失败", emptyList(), travelMode)
            }
        } finally {
            connection.disconnect()
        }
    }
    
    /**
     * 解析路线API响应
     */
    private fun parseRouteResponse(response: String, travelMode: String): RouteResult {
        val jsonObject = JSONObject(response)
        
        // 检查API响应状态
        val status = jsonObject.getString("status")
        if (status != "1") {
            val info = jsonObject.optString("info", "未知错误")
            Log.e(TAG, "API响应错误: $info")
            return RouteResult(0, 0, info, emptyList(), travelMode)
        }
        
        // 根据不同交通方式解析不同的响应结构
        return when (travelMode) {
            "walking" -> parseWalkingResponse(jsonObject, travelMode)
            "driving" -> parseDrivingResponse(jsonObject, travelMode)
            "transit" -> parseTransitResponse(jsonObject, travelMode)
            "riding" -> parseRidingResponse(jsonObject, travelMode)
            else -> RouteResult(0, 0, "不支持的交通方式", emptyList(), travelMode)
        }
    }
    
    /**
     * 解析步行路线响应
     */
    private fun parseWalkingResponse(jsonObject: JSONObject, travelMode: String): RouteResult {
        val route = jsonObject.getJSONObject("route")
        val paths = route.getJSONArray("paths")
        
        if (paths.length() > 0) {
            val path = paths.getJSONObject(0)
            val distance = path.getInt("distance")
            val duration = path.getInt("duration")
            
            // 解析路径点
            val steps = path.getJSONArray("steps")
            val pathPoints = mutableListOf<Location>()
            
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val polyline = step.getString("polyline")
                
                // 解析折线坐标
                polyline.split(";").forEach { point ->
                    val coordinates = point.split(",")
                    if (coordinates.size == 2) {
                        val lng = coordinates[0].toDoubleOrNull()
                        val lat = coordinates[1].toDoubleOrNull()
                        if (lng != null && lat != null) {
                            pathPoints.add(Location(lat, lng))
                        }
                    }
                }
            }
            
            return RouteResult(
                distance = distance,
                duration = duration,
                summary = "步行路线",
                path = pathPoints,
                travelMode = travelMode
            )
        }
        
        return RouteResult(0, 0, "未找到步行路线", emptyList(), travelMode)
    }
    
    /**
     * 解析驾车路线响应
     */
    private fun parseDrivingResponse(jsonObject: JSONObject, travelMode: String): RouteResult {
        val route = jsonObject.getJSONObject("route")
        val paths = route.getJSONArray("paths")
        
        if (paths.length() > 0) {
            val path = paths.getJSONObject(0)
            val distance = path.getInt("distance")
            val duration = path.getInt("duration")
            
            // 解析路径点
            val steps = path.getJSONArray("steps")
            val pathPoints = mutableListOf<Location>()
            
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val polyline = step.getString("polyline")
                
                // 解析折线坐标
                polyline.split(";").forEach { point ->
                    val coordinates = point.split(",")
                    if (coordinates.size == 2) {
                        val lng = coordinates[0].toDoubleOrNull()
                        val lat = coordinates[1].toDoubleOrNull()
                        if (lng != null && lat != null) {
                            pathPoints.add(Location(lat, lng))
                        }
                    }
                }
            }
            
            // 获取路线概述
            val trafficStatus = path.optString("traffic_condition", "未知")
            val summary = "驾车路线，交通状况: $trafficStatus"
            
            return RouteResult(
                distance = distance,
                duration = duration,
                summary = summary,
                path = pathPoints,
                travelMode = travelMode
            )
        }
        
        return RouteResult(0, 0, "未找到驾车路线", emptyList(), travelMode)
    }
    
    /**
     * 解析公交路线响应
     */
    private fun parseTransitResponse(jsonObject: JSONObject, travelMode: String): RouteResult {
        val route = jsonObject.getJSONObject("route")
        val transits = route.getJSONArray("transits")
        
        if (transits.length() > 0) {
            val transit = transits.getJSONObject(0)
            val distance = transit.getInt("distance")
            val duration = transit.getInt("duration")
            
            // 解析路径点（公交路线的路径点较为复杂，这里简化处理）
            val segments = transit.getJSONArray("segments")
            val pathPoints = mutableListOf<Location>()
            
            // 获取路线概述
            val cost = transit.optString("cost", "未知")
            val walking_distance = transit.optInt("walking_distance", 0)
            val summary = "公交路线，费用: $cost 元，步行距离: $walking_distance 米"
            
            return RouteResult(
                distance = distance,
                duration = duration,
                summary = summary,
                path = pathPoints,
                travelMode = travelMode
            )
        }
        
        return RouteResult(0, 0, "未找到公交路线", emptyList(), travelMode)
    }
    
    /**
     * 解析骑行路线响应
     */
    private fun parseRidingResponse(jsonObject: JSONObject, travelMode: String): RouteResult {
        val route = jsonObject.getJSONObject("route")
        val paths = route.getJSONArray("paths")
        
        if (paths.length() > 0) {
            val path = paths.getJSONObject(0)
            val distance = path.getInt("distance")
            val duration = path.getInt("duration")
            
            // 解析路径点
            val steps = path.getJSONArray("steps")
            val pathPoints = mutableListOf<Location>()
            
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val polyline = step.getString("polyline")
                
                // 解析折线坐标
                polyline.split(";").forEach { point ->
                    val coordinates = point.split(",")
                    if (coordinates.size == 2) {
                        val lng = coordinates[0].toDoubleOrNull()
                        val lat = coordinates[1].toDoubleOrNull()
                        if (lng != null && lat != null) {
                            pathPoints.add(Location(lat, lng))
                        }
                    }
                }
            }
            
            return RouteResult(
                distance = distance,
                duration = duration,
                summary = "骑行路线",
                path = pathPoints,
                travelMode = travelMode
            )
        }
        
        return RouteResult(0, 0, "未找到骑行路线", emptyList(), travelMode)
    }
    
    /**
     * 使用离线数据计算路线
     */
    private fun calculateOfflineRoute(
        origin: Location,
        destination: Location,
        travelMode: String
    ): RouteResult {
        // 离线路线计算的简化实现
        // 实际应用中应该使用离线地图SDK或自定义算法
        
        // 计算直线距离
        val distance = calculateDistance(origin, destination)
        
        // 根据交通方式估算时间
        val speed = when (travelMode) {
            "walking" -> 1.2 // 步行速度 1.2 m/s
            "driving" -> 8.3 // 驾车速度 30 km/h = 8.3 m/s
            "transit" -> 5.5 // 公交速度 20 km/h = 5.5 m/s
            "riding" -> 3.0 // 骑行速度 10.8 km/h = 3 m/s
            else -> 5.0
        }
        
        val duration = (distance / speed).toInt()
        
        return RouteResult(
            distance = distance.toInt(),
            duration = duration,
            summary = "离线${getLocalizedTravelMode(travelMode)}路线",
            path = listOf(origin, destination), // 简化为直线
            travelMode = travelMode
        )
    }
    
    /**
     * 计算两点间的直线距离（米）
     */
    private fun calculateDistance(start: Location, end: Location): Double {
        val earthRadius = 6371000.0 // 地球半径，单位：米
        
        val lat1Rad = Math.toRadians(start.latitude)
        val lat2Rad = Math.toRadians(end.latitude)
        val lon1Rad = Math.toRadians(start.longitude)
        val lon2Rad = Math.toRadians(end.longitude)
        
        val dLat = lat2Rad - lat1Rad
        val dLon = lon2Rad - lon1Rad
        
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        
        return earthRadius * c
    }
    
    /**
     * 获取交通方式的本地化名称
     */
    private fun getLocalizedTravelMode(travelMode: String): String {
        return when (travelMode) {
            "walking" -> "步行"
            "driving" -> "驾车"
            "transit" -> "公交"
            "riding" -> "骑行"
            else -> travelMode
        }
    }
    
    /**
     * 地理编码：地址转坐标
     * @param address 地址字符串
     * @return 位置坐标
     */
    override suspend fun geocode(address: String): Location? = withContext(Dispatchers.IO) {
        try {
            val encodedAddress = java.net.URLEncoder.encode(address, "UTF-8")
            val urlString = "https://restapi.amap.com/v3/geocode/geo?" +
                    "address=$encodedAddress" +
                    "&output=json" +
                    "&key=$apiKey"
            
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(response)
                    
                    val status = jsonObject.getString("status")
                    if (status == "1") {
                        val geocodes = jsonObject.getJSONArray("geocodes")
                        if (geocodes.length() > 0) {
                            val geocode = geocodes.getJSONObject(0)
                            val location = geocode.getString("location")
                            val coordinates = location.split(",")
                            
                            if (coordinates.size == 2) {
                                val lng = coordinates[0].toDoubleOrNull()
                                val lat = coordinates[1].toDoubleOrNull()
                                if (lng != null && lat != null) {
                                    return@withContext Location(lat, lng)
                                }
                            }
                        }
                    }
                }
                return@withContext null
            } finally {
                connection.disconnect()
            }
        } catch (e: Exception) {
            Log.e(TAG, "地理编码失败: ${e.message}")
            return@withContext null
        }
    }
    
    /**
     * 反向地理编码：坐标转地址
     * @param location 位置坐标
     * @return 地址字符串
     */
    override suspend fun reverseGeocode(location: Location): String? = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://restapi.amap.com/v3/geocode/regeo?" +
                    "location=${location.longitude},${location.latitude}" +
                    "&output=json" +
                    "&key=$apiKey"
            
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(response)
                    
                    val status = jsonObject.getString("status")
                    if (status == "1") {
                        val regeocode = jsonObject.getJSONObject("regeocode")
               
(Content truncated due to size limit. Use line ranges to read in chunks)