package com.example.travelplanapp.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.travelplanapp.R
import com.example.travelplanapp.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 设置界面Fragment
 * 用于显示和修改应用设置
 */
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by viewModels()
    
    // 请求码
    private val REQUEST_RINGTONE = 1001
    private val REQUEST_COLOR_PICKER = 1002
    
    // 当前选择的颜色设置类型
    private var currentColorSettingType = ColorSettingType.DEPARTURE
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupMapProviderSettings()
        setupApiKeySettings()
        setupTimeSettings()
        setupTravelModeSettings()
        setupDistanceThresholdSettings()
        setupColorSettings()
        setupReminderTextSettings()
        setupAlarmSettings()
        setupLanguageSettings()
        
        observeViewModel()
    }
    
    /**
     * 设置地图提供商选项
     */
    private fun setupMapProviderSettings() {
        binding.mapProviderGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_amap -> viewModel.setMapProvider("amap")
                R.id.radio_bmap -> viewModel.setMapProvider("bmap")
            }
        }
    }
    
    /**
     * 设置API密钥输入和验证
     */
    private fun setupApiKeySettings() {
        // 高德地图API密钥
        binding.validateAmapKeyButton.setOnClickListener {
            val apiKey = binding.amapApiKeyInput.text.toString().trim()
            if (apiKey.isNotEmpty()) {
                viewModel.setAmapApiKey(apiKey)
            } else {
                Toast.makeText(requireContext(), R.string.api_key_empty, Toast.LENGTH_SHORT).show()
            }
        }
        
        // 百度地图API密钥
        binding.validateBmapKeyButton.setOnClickListener {
            val apiKey = binding.bmapApiKeyInput.text.toString().trim()
            if (apiKey.isNotEmpty()) {
                viewModel.setBmapApiKey(apiKey)
            } else {
                Toast.makeText(requireContext(), R.string.api_key_empty, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    /**
     * 设置时间相关选项
     */
    private fun setupTimeSettings() {
        binding.extraTimeSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val minutes = value.toInt()
                binding.extraTimeValue.text = "$minutes 分钟"
                viewModel.setExtraTimeMinutes(minutes)
            }
        }
    }
    
    /**
     * 设置交通方式选项
     */
    private fun setupTravelModeSettings() {
        binding.travelModeGroup.setOnCheckedChangeListener { _, checkedId ->
            val travelMode = when (checkedId) {
                R.id.radio_walking -> "walking"
                R.id.radio_driving -> "driving"
                R.id.radio_transit -> "transit"
                R.id.radio_riding -> "riding"
                else -> "driving"
            }
            viewModel.setDefaultTravelMode(travelMode)
        }
    }
    
    /**
     * 设置距离阈值选项
     */
    private fun setupDistanceThresholdSettings() {
        binding.distanceThresholdSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val meters = value.toInt()
                binding.distanceThresholdValue.text = "$meters 米"
                viewModel.setDistanceThreshold(meters)
            }
        }
    }
    
    /**
     * 设置颜色选项
     */
    private fun setupColorSettings() {
        // 出发状态颜色
        binding.departureColorButton.setOnClickListener {
            currentColorSettingType = ColorSettingType.DEPARTURE
            showColorPickerDialog(viewModel.departureStatusColor.value ?: "#4CAF50")
        }
        
        // 上车状态颜色
        binding.boardingColorButton.setOnClickListener {
            currentColorSettingType = ColorSettingType.BOARDING
            showColorPickerDialog(viewModel.boardingStatusColor.value ?: "#FFC107")
        }
        
        // 下车状态颜色
        binding.arrivalColorButton.setOnClickListener {
            currentColorSettingType = ColorSettingType.ARRIVAL
            showColorPickerDialog(viewModel.arrivalStatusColor.value ?: "#212121")
        }
    }
    
    /**
     * 显示颜色选择器对话框
     */
    private fun showColorPickerDialog(initialColor: String) {
        // 在实际应用中，这里应该使用颜色选择器库
        // 这里简化为一个选项列表
        val colors = arrayOf(
            Pair("#F44336", "红色"),
            Pair("#E91E63", "粉红色"),
            Pair("#9C27B0", "紫色"),
            Pair("#673AB7", "深紫色"),
            Pair("#3F51B5", "靛蓝色"),
            Pair("#2196F3", "蓝色"),
            Pair("#03A9F4", "浅蓝色"),
            Pair("#00BCD4", "青色"),
            Pair("#009688", "蓝绿色"),
            Pair("#4CAF50", "绿色"),
            Pair("#8BC34A", "浅绿色"),
            Pair("#CDDC39", "酸橙色"),
            Pair("#FFEB3B", "黄色"),
            Pair("#FFC107", "琥珀色"),
            Pair("#FF9800", "橙色"),
            Pair("#FF5722", "深橙色"),
            Pair("#795548", "棕色"),
            Pair("#9E9E9E", "灰色"),
            Pair("#607D8B", "蓝灰色"),
            Pair("#000000", "黑色")
        )
        
        val colorNames = colors.map { it.second }.toTypedArray()
        var selectedIndex = colors.indexOfFirst { it.first == initialColor }
        if (selectedIndex == -1) selectedIndex = 0
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_color)
            .setSingleChoiceItems(colorNames, selectedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton(R.string.confirm) { _, _ ->
                val selectedColor = colors[selectedIndex].first
                applySelectedColor(selectedColor)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    /**
     * 应用选择的颜色
     */
    private fun applySelectedColor(colorHex: String) {
        when (currentColorSettingType) {
            ColorSettingType.DEPARTURE -> {
                binding.departureColorPreview.setBackgroundColor(Color.parseColor(colorHex))
                viewModel.setDepartureStatusColor(colorHex)
            }
            ColorSettingType.BOARDING -> {
                binding.boardingColorPreview.setBackgroundColor(Color.parseColor(colorHex))
                viewModel.setBoardingStatusColor(colorHex)
            }
            ColorSettingType.ARRIVAL -> {
                binding.arrivalColorPreview.setBackgroundColor(Color.parseColor(colorHex))
                viewModel.setArrivalStatusColor(colorHex)
            }
        }
    }
    
    /**
     * 设置提醒文本选项
     */
    private fun setupReminderTextSettings() {
        // 出发提醒文本
        binding.departureReminderInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.departureReminderInput.text.toString().trim()
                if (text.isNotEmpty()) {
                    viewModel.setDepartureReminderText(text)
                }
            }
        }
        
        // 上车提醒文本
        binding.boardingReminderInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.boardingReminderInput.text.toString().trim()
                if (text.isNotEmpty()) {
                    viewModel.setBoardingReminderText(text)
                }
            }
        }
        
        // 下车提醒文本
        binding.arrivalReminderInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.arrivalReminderInput.text.toString().trim()
                if (text.isNotEmpty()) {
                    viewModel.setArrivalReminderText(text)
                }
            }
        }
    }
    
    /**
     * 设置闹钟选项
     */
    private fun setupAlarmSettings() {
        // 选择铃声
        binding.selectRingtoneButton.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.select_alarm_ringtone))
            
            val currentRingtoneUri = viewModel.alarmRingtoneUri.value
            if (!currentRingtoneUri.isNullOrEmpty()) {
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(currentRingtoneUri))
            }
            
            startActivityForResult(intent, REQUEST_RINGTONE)
        }
        
        // 震动强度
        binding.vibrationStrengthSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val strength = value.toInt()
                binding.vibrationStrengthValue.text = "$strength%"
                viewModel.setAlarmVibrationStrength(strength)
            }
        }
    }
    
    /**
     * 设置语言选项
     */
    private fun setupLanguageSettings() {
        binding.languageGroup.setOnCheckedChangeListener { _, checkedId ->
            val languageCode = when (checkedId) {
                R.id.radio_chinese -> "zh"
                R.id.radio_english -> "en"
                else -> "zh"
            }
            viewModel.setAppLanguage(languageCode)
            
            // 提示用户需要重启应用以应用语言设置
            Toast.makeText(requireContext(), R.string.language_change_restart, Toast.LENGTH_LONG).show()
        }
    }
    
    /**
     * 观察ViewModel数据变化
     */
    private fun observeViewModel() {
        // 地图提供商
        viewModel.mapProvider.observe(viewLifecycleOwner) { provider ->
            when (provider) {
                "amap" -> binding.radioAmap.isChecked = true
                "bmap" -> binding.radioBmap.isChecked = true
            }
        }
        
        // API密钥
        viewModel.amapApiKey.observe(viewLifecycleOwner) { apiKey ->
            binding.amapApiKeyInput.setText(apiKey)
        }
        
        viewModel.bmapApiKey.observe(viewLifecycleOwner) { apiKey ->
            binding.bmapApiKeyInput.setText(apiKey)
        }
        
        // API密钥验证状态
        viewModel.apiKeyValidationStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                SettingsViewModel.ApiKeyValidationStatus.Validating -> {
                    Toast.makeText(requireContext(), R.string.validating_api_key, Toast.LENGTH_SHORT).show()
                }
                SettingsViewModel.ApiKeyValidationStatus.Valid -> {
                    Toast.makeText(requireContext(), R.string.api_key_valid, Toast.LENGTH_SHORT).show()
                }
                SettingsViewModel.ApiKeyValidationStatus.Invalid -> {
                    Toast.makeText(requireContext(), R.string.api_key_invalid, Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        // 预留空余时间
        viewModel.extraTimeMinutes.observe(viewLifecycleOwner) { minutes ->
            binding.extraTimeSlider.value = minutes.toFloat()
            binding.extraTimeValue.text = "$minutes 分钟"
        }
        
        // 默认交通方式
        viewModel.defaultTravelMode.observe(viewLifecycleOwner) { mode ->
            val radioButton = when (mode) {
                "walking" -> binding.radioWalking
                "driving" -> binding.radioDriving
                "transit" -> binding.radioTransit
                "riding" -> binding.radioRiding
                else -> binding.radioDriving
            }
            radioButton.isChecked = true
        }
        
        // 距离阈值
        viewModel.distanceThreshold.observe(viewLifecycleOwner) { meters ->
            binding.distanceThresholdSlider.value = meters.toFloat()
            binding.distanceThresholdValue.text = "$meters 米"
        }
        
        // 状态颜色
        viewModel.departureStatusColor.observe(viewLifecycleOwner) { colorHex ->
            binding.departureColorPreview.setBackgroundColor(Color.parseColor(colorHex))
        }
        
        viewModel.boardingStatusColor.observe(viewLifecycleOwner) { colorHex ->
            binding.boardingColorPreview.setBackgroundColor(Color.parseColor(colorHex))
        }
        
        viewModel.arrivalStatusColor.observe(viewLifecycleOwner) { colorHex ->
            binding.arrivalColorPreview.setBackgroundColor(Color.parseColor(colorHex))
        }
        
        // 提醒文本
        viewModel.departureReminderText.observe(viewLifecycleOwner) { text ->
            if (binding.departureReminderInput.text.toString() != text) {
                binding.departureReminderInput.setText(text)
            }
        }
        
        viewModel.boardingReminderText.observe(viewLifecycleOwner) { text ->
            if (binding.boardingReminderInput.text.toString() != text) {
                binding.boardingReminderInput.setText(text)
            }
        }
        
        viewModel.arrivalReminderText.observe(viewLifecycleOwner) { text ->
            if (binding.arrivalReminderInput.text.toString() != text) {
                binding.arrivalReminderInput.setText(text)
            }
        }
        
        // 震动强度
        viewModel.alarmVibrationStrength.observe(viewLifecycleOwner) { strength ->
            binding.vibrationStrengthSlider.value = strength.toFloat()
            binding.vibrationStrengthValue.text = "$strength%"
        }
        
        // 应用语言
        viewModel.appLanguage.observe(viewLifecycleOwner) { languageCode ->
            val radioButton = when (languageCode) {
                "zh" -> binding.radioChinese
                "en" -> binding.radioEnglish
                else -> binding.radioChinese
            }
            radioButton.isChecked = true
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_RINGTONE -> {
                    val ringtoneUri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                    ringtoneUri?.let {
                        viewModel.setAlarmRingtoneUri(it.toString())
                    }
                }
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    /**
     * 颜色设置类型枚举
     */
    enum class ColorSettingType {
        DEPARTURE, // 出发状态
        BOARDING,  // 上车状态
        ARRIVAL    // 下车状态
    }
}
