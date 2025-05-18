package com.example.travelplanapp.map

import android.content.Context
import android.util.Log
import com.example.travelplanapp.data.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 离线地图管理器
 * 负责离线地图数据的下载、存储和管理
 */
@Singleton
class OfflineMapManager @Inject constructor(
    private val context: Context
) {
    private val TAG = "OfflineMapManager"
    
    // 离线地图数据存储目录
    private val offlineMapDir: File by lazy {
        File(context.filesDir, "offline_maps").apply {
            if (!exists()) {
                mkdirs()
            }
        }
    }
    
    // 离线地图元数据文件
    private val metadataFile: File by lazy {
        File(offlineMapDir, "metadata.json")
    }
    
    // 已下载的离线地图区域
    private var downloadedAreas = mutableMapOf<String, OfflineMapArea>()
    
    init {
        loadMetadata()
    }
    
    /**
     * 加载离线地图元数据
     */
    private fun loadMetadata() {
        try {
            if (metadataFile.exists()) {
                val jsonString = metadataFile.readText()
                val jsonObject = JSONObject(jsonString)
                val areasArray = jsonObject.getJSONArray("areas")
                
                downloadedAreas.clear()
                for (i in 0 until areasArray.length()) {
                    val areaObject = areasArray.getJSONObject(i)
                    val area = OfflineMapArea(
                        id = areaObject.getString("id"),
                        name = areaObject.getString("name"),
                        size = areaObject.getLong("size"),
                        downloadDate = areaObject.getLong("downloadDate"),
                        boundNorth = areaObject.getDouble("boundNorth"),
                        boundSouth = areaObject.getDouble("boundSouth"),
                        boundEast = areaObject.getDouble("boundEast"),
                        boundWest = areaObject.getDouble("boundWest"),
                        provider = areaObject.getString("provider")
                    )
                    downloadedAreas[area.id] = area
                }
                
                Log.d(TAG, "已加载${downloadedAreas.size}个离线地图区域")
            }
        } catch (e: Exception) {
            Log.e(TAG, "加载离线地图元数据失败: ${e.message}")
        }
    }
    
    /**
     * 保存离线地图元数据
     */
    private fun saveMetadata() {
        try {
            val jsonObject = JSONObject()
            val areasArray = org.json.JSONArray()
            
            downloadedAreas.values.forEach { area ->
                val areaObject = JSONObject()
                areaObject.put("id", area.id)
                areaObject.put("name", area.name)
                areaObject.put("size", area.size)
                areaObject.put("downloadDate", area.downloadDate)
                areaObject.put("boundNorth", area.boundNorth)
                areaObject.put("boundSouth", area.boundSouth)
                areaObject.put("boundEast", area.boundEast)
                areaObject.put("boundWest", area.boundWest)
                areaObject.put("provider", area.provider)
                areasArray.put(areaObject)
            }
            
            jsonObject.put("areas", areasArray)
            
            FileOutputStream(metadataFile).use { fos ->
                fos.write(jsonObject.toString().toByteArray())
            }
            
            Log.d(TAG, "离线地图元数据保存成功")
        } catch (e: Exception) {
            Log.e(TAG, "保存离线地图元数据失败: ${e.message}")
        }
    }
    
    /**
     * 获取所有已下载的离线地图区域
     * @return 离线地图区域列表
     */
    fun getDownloadedAreas(): List<OfflineMapArea> {
        return downloadedAreas.values.toList()
    }
    
    /**
     * 检查指定位置是否有离线地图数据
     * @param location 位置坐标
     * @param provider 地图提供商
     * @return 是否有离线数据
     */
    fun hasOfflineData(location: Location, provider: String): Boolean {
        return downloadedAreas.values.any { area ->
            area.provider == provider &&
            location.latitude <= area.boundNorth &&
            location.latitude >= area.boundSouth &&
            location.longitude <= area.boundEast &&
            location.longitude >= area.boundWest
        }
    }
    
    /**
     * 获取指定位置的离线地图区域
     * @param location 位置坐标
     * @param provider 地图提供商
     * @return 离线地图区域，如果没有则返回null
     */
    fun getOfflineAreaForLocation(location: Location, provider: String): OfflineMapArea? {
        return downloadedAreas.values.find { area ->
            area.provider == provider &&
            location.latitude <= area.boundNorth &&
            location.latitude >= area.boundSouth &&
            location.longitude <= area.boundEast &&
            location.longitude >= area.boundWest
        }
    }
    
    /**
     * 下载离线地图区域
     * @param area 离线地图区域信息
     * @param mapData 地图数据
     * @return 下载是否成功
     */
    suspend fun downloadArea(area: OfflineMapArea, mapData: ByteArray): Boolean = withContext(Dispatchers.IO) {
        try {
            val areaFile = File(offlineMapDir, "${area.id}.map")
            
            FileOutputStream(areaFile).use { fos ->
                fos.write(mapData)
            }
            
            // 更新元数据
            downloadedAreas[area.id] = area
            saveMetadata()
            
            Log.d(TAG, "离线地图区域 ${area.name} 下载成功")
            return@withContext true
        } catch (e: IOException) {
            Log.e(TAG, "下载离线地图区域失败: ${e.message}")
            return@withContext false
        }
    }
    
    /**
     * 删除离线地图区域
     * @param areaId 离线地图区域ID
     * @return 删除是否成功
     */
    fun deleteArea(areaId: String): Boolean {
        try {
            val areaFile = File(offlineMapDir, "$areaId.map")
            if (areaFile.exists()) {
                areaFile.delete()
            }
            
            // 更新元数据
            downloadedAreas.remove(areaId)
            saveMetadata()
            
            Log.d(TAG, "离线地图区域 $areaId 删除成功")
            return true
        } catch (e: Exception) {
            Log.e(TAG, "删除离线地图区域失败: ${e.message}")
            return false
        }
    }
    
    /**
     * 获取离线地图数据
     * @param areaId 离线地图区域ID
     * @return 地图数据，如果不存在则返回null
     */
    suspend fun getMapData(areaId: String): ByteArray? = withContext(Dispatchers.IO) {
        try {
            val areaFile = File(offlineMapDir, "$areaId.map")
            if (areaFile.exists()) {
                return@withContext areaFile.readBytes()
            }
            return@withContext null
        } catch (e: Exception) {
            Log.e(TAG, "获取离线地图数据失败: ${e.message}")
            return@withContext null
        }
    }
    
    /**
     * 获取离线地图总大小
     * @return 总大小（字节）
     */
    fun getTotalSize(): Long {
        return downloadedAreas.values.sumOf { it.size }
    }
    
    /**
     * 清除所有离线地图数据
     * @return 清除是否成功
     */
    fun clearAllData(): Boolean {
        try {
            offlineMapDir.listFiles()?.forEach { file ->
                file.delete()
            }
            
            downloadedAreas.clear()
            saveMetadata()
            
            Log.d(TAG, "所有离线地图数据已清除")
            return true
        } catch (e: Exception) {
            Log.e(TAG, "清除离线地图数据失败: ${e.message}")
            return false
        }
    }
}

/**
 * 离线地图区域数据类
 */
data class OfflineMapArea(
    val id: String,                // 区域ID
    val name: String,              // 区域名称
    val size: Long,                // 数据大小（字节）
    val downloadDate: Long,        // 下载日期（时间戳）
    val boundNorth: Double,        // 北边界纬度
    val boundSouth: Double,        // 南边界纬度
    val boundEast: Double,         // 东边界经度
    val boundWest: Double,         // 西边界经度
    val provider: String           // 地图提供商
) {
    /**
     * 获取格式化的大小字符串
     * @return 格式化的大小字符串
     */
    fun getFormattedSize(): String {
        return when {
            size < 1024 -> "$size B"
            size < 1024 * 1024 -> "${size / 1024} KB"
            else -> "${size / (1024 * 1024)} MB"
        }
    }
    
    /**
     * 获取格式化的下载日期
     * @return 格式化的下载日期
     */
    fun getFormattedDownloadDate(): String {
        val date = java.util.Date(downloadDate)
        val format = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
        return format.format(date)
    }
}
