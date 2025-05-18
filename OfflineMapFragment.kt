package com.example.travelplanapp.ui.offline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplanapp.R
import com.example.travelplanapp.databinding.FragmentOfflineMapBinding
import com.example.travelplanapp.map.OfflineMapArea
import com.example.travelplanapp.ui.offline.adapter.OfflineMapAreaAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * 离线地图Fragment
 * 用于管理离线地图数据
 */
@AndroidEntryPoint
class OfflineMapFragment : Fragment() {

    private var _binding: FragmentOfflineMapBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: OfflineMapViewModel by viewModels()
    private lateinit var offlineMapAreaAdapter: OfflineMapAreaAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfflineMapBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupOfflineModeSwitch()
        setupRadiusSlider()
        setupButtons()
        observeViewModel()
    }
    
    /**
     * 设置RecyclerView
     */
    private fun setupRecyclerView() {
        offlineMapAreaAdapter = OfflineMapAreaAdapter(
            onDeleteClick = { area ->
                showDeleteConfirmationDialog(area)
            }
        )
        
        binding.offlineAreasRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = offlineMapAreaAdapter
        }
    }
    
    /**
     * 设置离线模式开关
     */
    private fun setupOfflineModeSwitch() {
        binding.offlineModeSwitch.isChecked = viewModel.isOfflineModeEnabled()
        
        binding.offlineModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setOfflineMode(isChecked)
        }
    }
    
    /**
     * 设置半径滑块
     */
    private fun setupRadiusSlider() {
        binding.radiusSlider.value = 10f
        binding.radiusValueText.text = getString(R.string.radius_value_format, 10)
        
        binding.radiusSlider.addOnChangeListener { _, value, _ ->
            binding.radiusValueText.text = getString(R.string.radius_value_format, value.toInt())
        }
    }
    
    /**
     * 设置按钮
     */
    private fun setupButtons() {
        // 清除所有离线数据按钮
        binding.clearAllButton.setOnClickListener {
            showClearAllConfirmationDialog()
        }
        
        // 下载按钮
        binding.downloadButton.setOnClickListener {
            val areaName = binding.areaNameInput.text.toString().trim()
            val centerLocation = binding.areaCenterInput.text.toString().trim()
            val radius = binding.radiusSlider.value.toInt()
            
            if (areaName.isEmpty() || centerLocation.isEmpty()) {
                Toast.makeText(requireContext(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val provider = when (binding.mapProviderGroup.checkedRadioButtonId) {
                R.id.radio_amap -> "amap"
                R.id.radio_bmap -> "bmap"
                else -> "amap" // 默认高德地图
            }
            
            viewModel.downloadOfflineMap(areaName, centerLocation, radius, provider)
        }
    }
    
    /**
     * 观察ViewModel数据变化
     */
    private fun observeViewModel() {
        // 观察离线地图区域列表
        viewModel.offlineMapAreas.observe(viewLifecycleOwner) { areas ->
            offlineMapAreaAdapter.submitList(areas)
            
            // 显示空视图或列表
            if (areas.isEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
                binding.offlineAreasRecyclerView.visibility = View.GONE
            } else {
                binding.emptyView.visibility = View.GONE
                binding.offlineAreasRecyclerView.visibility = View.VISIBLE
            }
        }
        
        // 观察总大小
        viewModel.totalSize.observe(viewLifecycleOwner) { size ->
            binding.totalSizeText.text = formatSize(size)
        }
        
        // 观察下载状态
        viewModel.downloadStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is OfflineMapViewModel.DownloadStatus.Downloading -> {
                    binding.downloadButton.isEnabled = false
                    binding.downloadButton.text = getString(R.string.downloading_progress, status.progress)
                }
                is OfflineMapViewModel.DownloadStatus.Success -> {
                    binding.downloadButton.isEnabled = true
                    binding.downloadButton.text = getString(R.string.download)
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                    
                    // 清空输入
                    binding.areaNameInput.text?.clear()
                    binding.areaCenterInput.text?.clear()
                    binding.radiusSlider.value = 10f
                }
                is OfflineMapViewModel.DownloadStatus.Error -> {
                    binding.downloadButton.isEnabled = true
                    binding.downloadButton.text = getString(R.string.download)
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }
                null -> {
                    binding.downloadButton.isEnabled = true
                    binding.downloadButton.text = getString(R.string.download)
                }
            }
        }
    }
    
    /**
     * 显示删除确认对话框
     */
    private fun showDeleteConfirmationDialog(area: OfflineMapArea) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_offline_area)
            .setMessage(getString(R.string.delete_offline_area_confirmation, area.name))
            .setPositiveButton(R.string.confirm) { _, _ ->
                viewModel.deleteOfflineMap(area.id)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    /**
     * 显示清除所有确认对话框
     */
    private fun showClearAllConfirmationDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(R.string.clear_all_offline_data)
            .setMessage(R.string.clear_all_offline_data_confirmation)
            .setPositiveButton(R.string.confirm) { _, _ ->
                viewModel.clearAllOfflineData()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    /**
     * 格式化大小
     */
    private fun formatSize(size: Long): String {
        return when {
            size < 1024 -> "$size B"
            size < 1024 * 1024 -> "${size / 1024} KB"
            else -> "${size / (1024 * 1024)} MB"
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
