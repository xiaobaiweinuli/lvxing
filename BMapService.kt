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
 * 百度地图服务实现类
 * 实现MapService接口，提供百度地图相关功能
 */
@Singleton
class BMapService @Inject constructor() : MapService {
    
    private val TAG = "BMapService"
    private var apiKey: String = ""
    private var isOfflineMode: Boolean = false
    
    /**
     * 初始化百度地图服务
     * @param apiKey 百度地图API密钥
     * @return 初始化是否成功
     */
    override suspend fun initialize(apiKey: String): Boolean {
        return try {
            this.apiKey = apiKey
            // 验证API密钥是否有效
            val isValid = validateApiKey(apiKey)
            if (isValid) {
                Log.d(TAG, "百度地图API初始化成功")
            } else {
                Log.e(TAG, "百度地图API密钥无效")
            }
            isValid
        } catch (e: Exception) {
            Log.e(TAG, "百度地图API初始化失败: ${e.message}")
            false
        }
    }
    
    /**
     * 获取当前地图服务提供商名称
     * @return 地图服务提供商名称
     */
    override fun getProviderName(): String = "百度地图"
    
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
        // 百度地图坐标系转换（WGS84转BD09）
        val originBD = wgs84ToBd09(origin)
        val destinationBD = wgs84ToBd09(destination)
        
        // 根据交通方式选择对应的API
        val apiType = when (travelMode) {
            "walking" -> "walking"
            "driving" -> "driving"
            "transit" -> "transit"
            "riding" -> "riding"
            else -> "driving"
        }
        
        // 构建API请求URL
        val urlString = "https://api.map.baidu.com/direction/v2/$apiType?" +
                "origin=${originBD.latitude},${originBD.longitude}" +
                "&destination=${destinationBD.latitude},${destinationBD.longitude}" +
                "&ak=$apiKey" +
                "&output=json"
        
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
        val status = jsonObject.getInt("status")
        if (status != 0) {
            val message = jsonObject.optString("message", "未知错误")
            Log.e(TAG, "API响应错误: $message")
            return RouteResult(0, 0, message, emptyList(), travelMode)
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
        val result = jsonObject.getJSONObject("result")
        val routes = result.getJSONArray("routes")
        
        if (routes.length() > 0) {
            val route = routes.getJSONObject(0)
            val distance = route.getInt("distance")
            val duration = route.getInt("duration")
            
            // 解析路径点
            val steps = route.getJSONArray("steps")
            val pathPoints = mutableListOf<Location>()
            
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val path = step.getString("path")
                
                // 解析折线坐标
                path.split(";").forEach { point ->
                    val coordinates = point.split(",")
                    if (coordinates.size == 2) {
                        val lng = coordinates[0].toDoubleOrNull()
                        val lat = coordinates[1].toDoubleOrNull()
                        if (lng != null && lat != null) {
                            // 百度坐标系转WGS84
                            pathPoints.add(bd09ToWgs84(Location(lat, lng)))
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
        val result = jsonObject.getJSONObject("result")
        val routes = result.getJSONArray("routes")
        
        if (routes.length() > 0) {
            val route = routes.getJSONObject(0)
            val distance = route.getInt("distance")
            val duration = route.getInt("duration")
            
            // 解析路径点
            val steps = route.getJSONArray("steps")
            val pathPoints = mutableListOf<Location>()
            
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val path = step.getString("path")
                
                // 解析折线坐标
                path.split(";").forEach { point ->
                    val coordinates = point.split(",")
                    if (coordinates.size == 2) {
                        val lng = coordinates[0].toDoubleOrNull()
                        val lat = coordinates[1].toDoubleOrNull()
                        if (lng != null && lat != null) {
                            // 百度坐标系转WGS84
                            pathPoints.add(bd09ToWgs84(Location(lat, lng)))
                        }
                    }
                }
            }
            
            // 获取路线概述
            val trafficCondition = route.optString("traffic_condition", "未知")
            val summary = "驾车路线，交通状况: $trafficCondition"
            
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
        val result = jsonObject.getJSONObject("result")
        val routes = result.getJSONArray("routes")
        
        if (routes.length() > 0) {
            val route = routes.getJSONObject(0)
            val distance = route.getInt("distance")
            val duration = route.getInt("duration")
            
            // 解析路径点（公交路线的路径点较为复杂，这里简化处理）
            val pathPoints = mutableListOf<Location>()
            
            // 获取路线概述
            val price = route.optInt("price", 0)
            val walkingDistance = route.optInt("walking_distance", 0)
            val summary = "公交路线，费用: $price 元，步行距离: $walkingDistance 米"
            
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
        val result = jsonObject.getJSONObject("result")
        val routes = result.getJSONArray("routes")
        
        if (routes.length() > 0) {
            val route = routes.getJSONObject(0)
            val distance = route.getInt("distance")
            val duration = route.getInt("duration")
            
            // 解析路径点
            val pathPoints = mutableListOf<Location>()
            val steps = route.getJSONArray("steps")
            
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val path = step.getString("path")
                
                // 解析折线坐标
                path.split(";").forEach { point ->
                    val coordinates = point.split(",")
                    if (coordinates.size == 2) {
                        val lng = coordinates[0].toDoubleOrNull()
                        val lat = coordinates[1].toDoubleOrNull()
                        if (lng != null && lat != null) {
                            // 百度坐标系转WGS84
                            pathPoints.add(bd09ToWgs84(Location(lat, lng)))
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
            val urlString = "https://api.map.baidu.com/geocoding/v3/?" +
                    "address=$encodedAddress" +
                    "&output=json" +
                    "&ak=$apiKey"
            
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(response)
                    
                    val status = jsonObject.getInt("status")
                    if (status == 0) {
                        val result = jsonObject.getJSONObject("result")
                        val location = result.getJSONObject("location")
                        val lng = location.getDouble("lng")
                        val lat = location.getDouble("lat")
                        
                        // 百度坐标系转WGS84
                        return@withContext bd09ToWgs84(Location(lat, lng))
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
            // WGS84转百度坐标系
            val bdLocation = wgs84ToBd09(location)
            
            val urlString = "https://api.map.baidu.com/reverse_geocoding/v3/?" +
                    "location=${bdLocation.latitude},${bdLocation.longitude}" +
                    "&output=json" +
                    "&ak=$apiKey"
            
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(response)
                    
                    val status = jsonObject.getInt("status")
                    if (status == 0) {
                        val result = jsonObject.getJSONObject("result")
                        return@withContext result.getString("formatted_address")
     
(Content truncated due to size limit. Use line ranges to read in chunks)