package com.example.travelplanapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户偏好设置管理器
 * 负责存储和管理用户的偏好设置
 */
@Singleton
class UserPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // DataStore实例
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
    
    // 偏好设置键
    companion object {
        // 地图设置
        private val MAP_API_KEY = stringPreferencesKey("map_api_key")
        private val MAP_PROVIDER = stringPreferencesKey("map_provider")
        private val OFFLINE_MODE = booleanPreferencesKey("offline_mode")
        
        // 闹钟设置
        private val DEFAULT_SPARE_TIME = intPreferencesKey("default_spare_time")
        private val DEFAULT_TRAVEL_MODE = stringPreferencesKey("default_travel_mode")
        private val DISTANCE_THRESHOLD = floatPreferencesKey("distance_threshold")
        private val VIBRATION_INTENSITY = intPreferencesKey("vibration_intensity")
        private val RINGTONE_URI = stringPreferencesKey("ringtone_uri")
        
        // 通知设置
        private val DEPARTURE_NOTIFICATION = stringPreferencesKey("departure_notification")
        private val BOARDING_NOTIFICATION = stringPreferencesKey("boarding_notification")
        private val ARRIVAL_NOTIFICATION = stringPreferencesKey("arrival_notification")
        
        // 状态颜色设置
        private val ACTIVE_GREEN_COLOR = stringPreferencesKey("active_green_color")
        private val ACTIVE_RED_COLOR = stringPreferencesKey("active_red_color")
        private val ACTIVE_YELLOW_COLOR = stringPreferencesKey("active_yellow_color")
        private val ACTIVE_BLACK_COLOR = stringPreferencesKey("active_black_color")
        
        // UI自定义设置
        private val UI_ELEMENT_POSITION = stringPreferencesKey("ui_element_position")
        private val UI_ELEMENT_SHAPE = stringPreferencesKey("ui_element_shape")
        private val UI_ELEMENT_COLOR = stringPreferencesKey("ui_element_color")
        private val UI_ELEMENT_BACKGROUND = stringPreferencesKey("ui_element_background")
        
        // 语言设置
        private val APP_LANGUAGE = stringPreferencesKey("app_language")
    }
    
    /**
     * 获取地图API密钥
     * @param provider 地图提供商
     * @return API密钥
     */
    suspend fun getMapApiKey(provider: String): String {
        return context.dataStore.data.map { preferences ->
            preferences[MAP_API_KEY] ?: ""
        }.collect { it }
    }
    
    /**
     * 设置地图API密钥
     * @param apiKey API密钥
     */
    suspend fun setMapApiKey(apiKey: String) {
        context.dataStore.edit { preferences ->
            preferences[MAP_API_KEY] = apiKey
        }
    }
    
    /**
     * 获取地图提供商
     * @return 地图提供商
     */
    fun getMapProvider(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[MAP_PROVIDER] ?: "amap" // 默认高德地图
        }
    }
    
    /**
     * 设置地图提供商
     * @param provider 地图提供商
     */
    suspend fun setMapProvider(provider: String) {
        context.dataStore.edit { preferences ->
            preferences[MAP_PROVIDER] = provider
        }
    }
    
    /**
     * 获取离线模式状态
     * @return 是否启用离线模式
     */
    fun getOfflineMode(): Boolean {
        // 同步获取，用于初始化
        return false // 默认不启用离线模式
    }
    
    /**
     * 获取离线模式状态Flow
     * @return 离线模式状态Flow
     */
    fun getOfflineModeFlow(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[OFFLINE_MODE] ?: false
        }
    }
    
    /**
     * 设置离线模式
     * @param enabled 是否启用
     */
    fun setOfflineMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[OFFLINE_MODE] = enabled
        }
    }
    
    /**
     * 获取默认空余时间（分钟）
     * @return 默认空余时间
     */
    fun getDefaultSpareTime(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[DEFAULT_SPARE_TIME] ?: 45 // 默认45分钟
        }
    }
    
    /**
     * 设置默认空余时间
     * @param minutes 分钟数
     */
    suspend fun setDefaultSpareTime(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_SPARE_TIME] = minutes
        }
    }
    
    /**
     * 获取默认交通方式
     * @return 默认交通方式
     */
    fun getDefaultTravelMode(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[DEFAULT_TRAVEL_MODE] ?: "driving" // 默认驾车
        }
    }
    
    /**
     * 设置默认交通方式
     * @param mode 交通方式
     */
    suspend fun setDefaultTravelMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_TRAVEL_MODE] = mode
        }
    }
    
    /**
     * 获取距离阈值（千米）
     * @return 距离阈值
     */
    fun getDistanceThreshold(): Flow<Float> {
        return context.dataStore.data.map { preferences ->
            preferences[DISTANCE_THRESHOLD] ?: 1.0f // 默认1千米
        }
    }
    
    /**
     * 设置距离阈值
     * @param kilometers 千米数
     */
    suspend fun setDistanceThreshold(kilometers: Float) {
        context.dataStore.edit { preferences ->
            preferences[DISTANCE_THRESHOLD] = kilometers
        }
    }
    
    /**
     * 获取震动强度
     * @return 震动强度
     */
    fun getVibrationIntensity(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[VIBRATION_INTENSITY] ?: 50 // 默认50%
        }
    }
    
    /**
     * 设置震动强度
     * @param intensity 强度（0-100）
     */
    suspend fun setVibrationIntensity(intensity: Int) {
        context.dataStore.edit { preferences ->
            preferences[VIBRATION_INTENSITY] = intensity.coerceIn(0, 100)
        }
    }
    
    /**
     * 获取铃声URI
     * @return 铃声URI
     */
    fun getRingtoneUri(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[RINGTONE_URI] ?: "" // 默认系统铃声
        }
    }
    
    /**
     * 设置铃声URI
     * @param uri 铃声URI
     */
    suspend fun setRingtoneUri(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[RINGTONE_URI] = uri
        }
    }
    
    /**
     * 获取出发通知内容
     * @return 通知内容
     */
    fun getDepartureNotification(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[DEPARTURE_NOTIFICATION] ?: context.getString(com.example.travelplanapp.R.string.time_to_leave)
        }
    }
    
    /**
     * 设置出发通知内容
     * @param content 通知内容
     */
    suspend fun setDepartureNotification(content: String) {
        context.dataStore.edit { preferences ->
            preferences[DEPARTURE_NOTIFICATION] = content
        }
    }
    
    /**
     * 获取上车通知内容
     * @return 通知内容
     */
    fun getBoardingNotification(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[BOARDING_NOTIFICATION] ?: context.getString(com.example.travelplanapp.R.string.time_to_board)
        }
    }
    
    /**
     * 设置上车通知内容
     * @param content 通知内容
     */
    suspend fun setBoardingNotification(content: String) {
        context.dataStore.edit { preferences ->
            preferences[BOARDING_NOTIFICATION] = content
        }
    }
    
    /**
     * 获取下车通知内容
     * @return 通知内容
     */
    fun getArrivalNotification(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ARRIVAL_NOTIFICATION] ?: context.getString(com.example.travelplanapp.R.string.time_to_get_off)
        }
    }
    
    /**
     * 设置下车通知内容
     * @param content 通知内容
     */
    suspend fun setArrivalNotification(content: String) {
        context.dataStore.edit { preferences ->
            preferences[ARRIVAL_NOTIFICATION] = content
        }
    }
    
    /**
     * 获取活动状态颜色（绿色）
     * @return 颜色值
     */
    fun getActiveGreenColor(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ACTIVE_GREEN_COLOR] ?: "#4CAF50" // 默认绿色
        }
    }
    
    /**
     * 设置活动状态颜色（绿色）
     * @param color 颜色值
     */
    suspend fun setActiveGreenColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[ACTIVE_GREEN_COLOR] = color
        }
    }
    
    /**
     * 获取活动状态颜色（红色）
     * @return 颜色值
     */
    fun getActiveRedColor(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ACTIVE_RED_COLOR] ?: "#F44336" // 默认红色
        }
    }
    
    /**
     * 设置活动状态颜色（红色）
     * @param color 颜色值
     */
    suspend fun setActiveRedColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[ACTIVE_RED_COLOR] = color
        }
    }
    
    /**
     * 获取活动状态颜色（黄色）
     * @return 颜色值
     */
    fun getActiveYellowColor(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ACTIVE_YELLOW_COLOR] ?: "#FFC107" // 默认黄色
        }
    }
    
    /**
     * 设置活动状态颜色（黄色）
     * @param color 颜色值
     */
    suspend fun setActiveYellowColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[ACTIVE_YELLOW_COLOR] = color
        }
    }
    
    /**
     * 获取活动状态颜色（黑色）
     * @return 颜色值
     */
    fun getActiveBlackColor(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ACTIVE_BLACK_COLOR] ?: "#212121" // 默认黑色
        }
    }
    
    /**
     * 设置活动状态颜色（黑色）
     * @param color 颜色值
     */
    suspend fun setActiveBlackColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[ACTIVE_BLACK_COLOR] = color
        }
    }
    
    /**
     * 获取UI元素位置
     * @return 位置JSON
     */
    fun getUiElementPosition(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[UI_ELEMENT_POSITION] ?: "{}" // 默认空JSON
        }
    }
    
    /**
     * 设置UI元素位置
     * @param positionJson 位置JSON
     */
    suspend fun setUiElementPosition(positionJson: String) {
        context.dataStore.edit { preferences ->
            preferences[UI_ELEMENT_POSITION] = positionJson
        }
    }
    
    /**
     * 获取UI元素形状
     * @return 形状JSON
     */
    fun getUiElementShape(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[UI_ELEMENT_SHAPE] ?: "{}" // 默认空JSON
        }
    }
    
    /**
     * 设置UI元素形状
     * @param shapeJson 形状JSON
     */
    suspend fun setUiElementShape(shapeJson: String) {
        context.dataStore.edit { preferences ->
            preferences[UI_ELEMENT_SHAPE] = shapeJson
        }
    }
    
    /**
     * 获取UI元素颜色
     * @return 颜色JSON
     */
    fun getUiElementColor(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[UI_ELEMENT_COLOR] ?: "{}" // 默认空JSON
        }
    }
    
    /**
     * 设置UI元素颜色
     * @param colorJson 颜色JSON
     */
    suspend fun setUiElementColor(colorJson: String) {
        context.dataStore.edit { preferences ->
            preferences[UI_ELEMENT_COLOR] = colorJson
        }
    }
    
    /**
     * 获取UI元素背景
     * @return 背景JSON
     */
    fun getUiElementBackground(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[UI_ELEMENT_BACKGROUND] ?: "{}" // 默认空JSON
        }
    }
    
    /**
     * 设置UI元素背景
     * @param backgroundJson 背景JSON
     */
    suspend fun setUiElementBackground(backgroundJson: String) {
        context.dataStore.edit { preferences ->
            preferences[UI_ELEMENT_BACKGROUND] = backgroundJson
        }
    }
    
    /**
     * 获取应用语言
     * @return 语言代码
     */
    fun getAppLanguage(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[APP_LANGUAGE] ?: "auto" // 默认跟随系统
        }
    }
    
    /**
     * 获取应用语言（同步方法）
     * @return 语言代码
     */
    fun getAppLanguageSync(): String {
        // 同步获取，用于初始化
        return "auto" // 默认跟随系统
    }
    
    /**
     * 设置应用语言
     * @param languageCode 语言代码
     */
    suspend fun setAppLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = languageCode
        }
    }
    
    /**
     * 获取当前Locale
     * @return 当前Locale
     */
    fun getCurrentLocale(): Locale {
        val languageCode = getAppLanguageSync()
        return when (languageCode) {
            "zh" -> Locale.SIMPLIFIED_CHINESE
            "en" -> Locale.ENGLISH
            else -> Locale.getDefault() // 跟随系统
        }
    }
    
    /**
     * 重置所有设置
     */
    suspend fun resetAllSettings() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
