package com.example.travelplanapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelplanapp.data.preferences.UserPreferencesManager
import com.example.travelplanapp.map.AMapService
import com.example.travelplanapp.map.BMapService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 设置界面的ViewModel
 * 处理用户设置相关的业务逻辑
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val aMapService: AMapService,
    private val bMapService: BMapService
) : ViewModel() {

    // 地图提供商设置
    private val _mapProvider = MutableLiveData<String>()
    val mapProvider: LiveData<String> = _mapProvider

    // 高德地图API密钥
    private val _amapApiKey = MutableLiveData<String>()
    val amapApiKey: LiveData<String> = _amapApiKey

    // 百度地图API密钥
    private val _bmapApiKey = MutableLiveData<String>()
    val bmapApiKey: LiveData<String> = _bmapApiKey

    // API密钥验证状态
    private val _apiKeyValidationStatus = MutableLiveData<ApiKeyValidationStatus>()
    val apiKeyValidationStatus: LiveData<ApiKeyValidationStatus> = _apiKeyValidationStatus

    // 预留空余时间(分钟)
    private val _extraTimeMinutes = MutableLiveData<Int>()
    val extraTimeMinutes: LiveData<Int> = _extraTimeMinutes

    // 默认交通方式
    private val _defaultTravelMode = MutableLiveData<String>()
    val defaultTravelMode: LiveData<String> = _defaultTravelMode

    // 距离阈值(米)
    private val _distanceThreshold = MutableLiveData<Int>()
    val distanceThreshold: LiveData<Int> = _distanceThreshold

    // 出发状态颜色
    private val _departureStatusColor = MutableLiveData<String>()
    val departureStatusColor: LiveData<String> = _departureStatusColor

    // 上车状态颜色
    private val _boardingStatusColor = MutableLiveData<String>()
    val boardingStatusColor: LiveData<String> = _boardingStatusColor

    // 下车状态颜色
    private val _arrivalStatusColor = MutableLiveData<String>()
    val arrivalStatusColor: LiveData<String> = _arrivalStatusColor

    // 出发提醒文本
    private val _departureReminderText = MutableLiveData<String>()
    val departureReminderText: LiveData<String> = _departureReminderText

    // 上车提醒文本
    private val _boardingReminderText = MutableLiveData<String>()
    val boardingReminderText: LiveData<String> = _boardingReminderText

    // 下车提醒文本
    private val _arrivalReminderText = MutableLiveData<String>()
    val arrivalReminderText: LiveData<String> = _arrivalReminderText

    // 闹钟铃声URI
    private val _alarmRingtoneUri = MutableLiveData<String>()
    val alarmRingtoneUri: LiveData<String> = _alarmRingtoneUri

    // 震动强度
    private val _alarmVibrationStrength = MutableLiveData<Int>()
    val alarmVibrationStrength: LiveData<Int> = _alarmVibrationStrength

    // 应用语言
    private val _appLanguage = MutableLiveData<String>()
    val appLanguage: LiveData<String> = _appLanguage

    init {
        loadSettings()
    }

    /**
     * 加载所有设置
     */
    private fun loadSettings() {
        viewModelScope.launch {
            _mapProvider.value = userPreferencesManager.mapProviderFlow.first()
            _amapApiKey.value = userPreferencesManager.amapApiKeyFlow.first()
            _bmapApiKey.value = userPreferencesManager.bmapApiKeyFlow.first()
            _extraTimeMinutes.value = userPreferencesManager.extraTimeMinutesFlow.first()
            _defaultTravelMode.value = userPreferencesManager.defaultTravelModeFlow.first()
            _distanceThreshold.value = userPreferencesManager.distanceThresholdFlow.first()
            _departureStatusColor.value = userPreferencesManager.departureStatusColorFlow.first()
            _boardingStatusColor.value = userPreferencesManager.boardingStatusColorFlow.first()
            _arrivalStatusColor.value = userPreferencesManager.arrivalStatusColorFlow.first()
            _departureReminderText.value = userPreferencesManager.departureReminderTextFlow.first()
            _boardingReminderText.value = userPreferencesManager.boardingReminderTextFlow.first()
            _arrivalReminderText.value = userPreferencesManager.arrivalReminderTextFlow.first()
            _alarmRingtoneUri.value = userPreferencesManager.alarmRingtoneUriFlow.first()
            _alarmVibrationStrength.value = userPreferencesManager.alarmVibrationStrengthFlow.first()
            _appLanguage.value = userPreferencesManager.appLanguageFlow.first()
        }
    }

    /**
     * 设置地图提供商
     * @param provider 地图提供商名称 ("amap" 或 "bmap")
     */
    fun setMapProvider(provider: String) {
        viewModelScope.launch {
            userPreferencesManager.setMapProvider(provider)
            _mapProvider.value = provider
        }
    }

    /**
     * 设置高德地图API密钥
     * @param apiKey 高德地图API密钥
     */
    fun setAmapApiKey(apiKey: String) {
        viewModelScope.launch {
            userPreferencesManager.setAmapApiKey(apiKey)
            _amapApiKey.value = apiKey
            validateApiKey("amap", apiKey)
        }
    }

    /**
     * 设置百度地图API密钥
     * @param apiKey 百度地图API密钥
     */
    fun setBmapApiKey(apiKey: String) {
        viewModelScope.launch {
            userPreferencesManager.setBmapApiKey(apiKey)
            _bmapApiKey.value = apiKey
            validateApiKey("bmap", apiKey)
        }
    }

    /**
     * 验证API密钥
     * @param provider 地图提供商名称 ("amap" 或 "bmap")
     * @param apiKey 要验证的API密钥
     */
    private suspend fun validateApiKey(provider: String, apiKey: String) {
        _apiKeyValidationStatus.value = ApiKeyValidationStatus.Validating

        val isValid = when (provider) {
            "amap" -> aMapService.validateApiKey(apiKey)
            "bmap" -> bMapService.validateApiKey(apiKey)
            else -> false
        }

        _apiKeyValidationStatus.value = if (isValid) {
            ApiKeyValidationStatus.Valid
        } else {
            ApiKeyValidationStatus.Invalid
        }
    }

    /**
     * 设置预留空余时间
     * @param minutes 预留分钟数
     */
    fun setExtraTimeMinutes(minutes: Int) {
        viewModelScope.launch {
            userPreferencesManager.setExtraTimeMinutes(minutes)
            _extraTimeMinutes.value = minutes
        }
    }

    /**
     * 设置默认交通方式
     * @param mode 交通方式 ("walking", "driving", "transit", "riding")
     */
    fun setDefaultTravelMode(mode: String) {
        viewModelScope.launch {
            userPreferencesManager.setDefaultTravelMode(mode)
            _defaultTravelMode.value = mode
        }
    }

    /**
     * 设置距离阈值
     * @param meters 距离阈值(米)
     */
    fun setDistanceThreshold(meters: Int) {
        viewModelScope.launch {
            userPreferencesManager.setDistanceThreshold(meters)
            _distanceThreshold.value = meters
        }
    }

    /**
     * 设置出发状态颜色
     * @param colorHex 颜色十六进制值
     */
    fun setDepartureStatusColor(colorHex: String) {
        viewModelScope.launch {
            userPreferencesManager.setDepartureStatusColor(colorHex)
            _departureStatusColor.value = colorHex
        }
    }

    /**
     * 设置上车状态颜色
     * @param colorHex 颜色十六进制值
     */
    fun setBoardingStatusColor(colorHex: String) {
        viewModelScope.launch {
            userPreferencesManager.setBoardingStatusColor(colorHex)
            _boardingStatusColor.value = colorHex
        }
    }

    /**
     * 设置下车状态颜色
     * @param colorHex 颜色十六进制值
     */
    fun setArrivalStatusColor(colorHex: String) {
        viewModelScope.launch {
            userPreferencesManager.setArrivalStatusColor(colorHex)
            _arrivalStatusColor.value = colorHex
        }
    }

    /**
     * 设置出发提醒文本
     * @param text 提醒文本
     */
    fun setDepartureReminderText(text: String) {
        viewModelScope.launch {
            userPreferencesManager.setDepartureReminderText(text)
            _departureReminderText.value = text
        }
    }

    /**
     * 设置上车提醒文本
     * @param text 提醒文本
     */
    fun setBoardingReminderText(text: String) {
        viewModelScope.launch {
            userPreferencesManager.setBoardingReminderText(text)
            _boardingReminderText.value = text
        }
    }

    /**
     * 设置下车提醒文本
     * @param text 提醒文本
     */
    fun setArrivalReminderText(text: String) {
        viewModelScope.launch {
            userPreferencesManager.setArrivalReminderText(text)
            _arrivalReminderText.value = text
        }
    }

    /**
     * 设置闹钟铃声URI
     * @param uri 铃声URI
     */
    fun setAlarmRingtoneUri(uri: String) {
        viewModelScope.launch {
            userPreferencesManager.setAlarmRingtoneUri(uri)
            _alarmRingtoneUri.value = uri
        }
    }

    /**
     * 设置震动强度
     * @param strength 震动强度(0-100)
     */
    fun setAlarmVibrationStrength(strength: Int) {
        viewModelScope.launch {
            userPreferencesManager.setAlarmVibrationStrength(strength)
            _alarmVibrationStrength.value = strength
        }
    }

    /**
     * 设置应用语言
     * @param languageCode 语言代码 (如 "zh", "en")
     */
    fun setAppLanguage(languageCode: String) {
        viewModelScope.launch {
            userPreferencesManager.setAppLanguage(languageCode)
            _appLanguage.value = languageCode
        }
    }

    /**
     * API密钥验证状态
     */
    enum class ApiKeyValidationStatus {
        Validating, // 验证中
        Valid,      // 有效
        Invalid     // 无效
    }
}
