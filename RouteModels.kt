package com.example.travelplanapp.map

/**
 * 交通方式枚举
 */
enum class TransportType {
    WALKING,    // 步行
    DRIVING,    // 驾车
    TRANSIT,    // 公交
    BIKING;     // 骑行
    
    /**
     * 获取交通方式的显示名称
     */
    fun getDisplayName(): String {
        return when (this) {
            WALKING -> "步行"
            DRIVING -> "驾车"
            TRANSIT -> "公交"
            BIKING -> "骑行"
        }
    }
}

/**
 * 路线点数据类
 * 
 * @property latitude 纬度
 * @property longitude 经度
 */
data class RoutePoint(
    val latitude: Double,
    val longitude: Double
)

/**
 * 路线结果数据类
 * 
 * @property distance 路线距离（米）
 * @property duration 预计用时（秒）
 * @property transportType 交通方式
 * @property routePoints 路线点列表
 */
data class RouteResult(
    val distance: Float,
    val duration: Float,
    val transportType: TransportType,
    val routePoints: List<RoutePoint>
) {
    companion object {
        /**
         * 创建空的路线结果
         * 
         * @param transportType 交通方式
         * @return 空的路线结果
         */
        fun empty(transportType: TransportType): RouteResult {
            return RouteResult(
                distance = 0f,
                duration = 0f,
                transportType = transportType,
                routePoints = emptyList()
            )
        }
    }
    
    /**
     * 获取格式化的距离字符串
     * 
     * @return 格式化的距离字符串（如：1.2公里、500米）
     */
    fun getFormattedDistance(): String {
        return when {
            distance < 1000 -> "${distance.toInt()}米"
            else -> String.format("%.1f公里", distance / 1000)
        }
    }
    
    /**
     * 获取格式化的用时字符串
     * 
     * @return 格式化的用时字符串（如：1小时20分钟、15分钟）
     */
    fun getFormattedDuration(): String {
        val minutes = (duration / 60).toInt()
        return when {
            minutes < 60 -> "${minutes}分钟"
            else -> {
                val hours = minutes / 60
                val remainMinutes = minutes % 60
                if (remainMinutes > 0) {
                    "${hours}小时${remainMinutes}分钟"
                } else {
                    "${hours}小时"
                }
            }
        }
    }
}
