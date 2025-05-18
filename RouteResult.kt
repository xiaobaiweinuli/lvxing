package com.example.travelplanapp.map

import com.example.travelplanapp.data.model.Location

/**
 * 路线计算结果数据类
 * 包含路线的距离、时间、路径点等信息
 */
data class RouteResult(
    /**
     * 路线距离，单位：米
     */
    val distance: Int,
    
    /**
     * 路线预计用时，单位：秒
     */
    val duration: Int,
    
    /**
     * 路线概述
     */
    val summary: String,
    
    /**
     * 路线路径点列表
     */
    val path: List<Location>,
    
    /**
     * 交通方式
     */
    val travelMode: String
) {
    /**
     * 获取格式化的距离字符串
     * @return 格式化的距离字符串，如"1.2公里"
     */
    fun getFormattedDistance(): String {
        return when {
            distance < 1000 -> "$distance 米"
            else -> String.format("%.1f 公里", distance / 1000.0)
        }
    }
    
    /**
     * 获取格式化的时间字符串
     * @return 格式化的时间字符串，如"1小时20分钟"
     */
    fun getFormattedDuration(): String {
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60
        
        return when {
            hours > 0 -> "$hours 小时 $minutes 分钟"
            else -> "$minutes 分钟"
        }
    }
    
    /**
     * 获取交通方式的本地化名称
     * @return 交通方式的本地化名称
     */
    fun getLocalizedTravelMode(): String {
        return when (travelMode) {
            "walking" -> "步行"
            "driving" -> "驾车"
            "transit" -> "公交"
            "riding" -> "骑行"
            else -> travelMode
        }
    }
}
