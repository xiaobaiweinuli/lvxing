package com.example.travelplanapp.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.travelplanapp.R
import com.example.travelplanapp.data.model.Location
import com.example.travelplanapp.databinding.FragmentRouteTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 路线计算测试Fragment
 * 用于测试不同地图API和交通方式的路线计算
 */
@AndroidEntryPoint
class RouteTestFragment : Fragment() {

    private var _binding: FragmentRouteTestBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: RouteTestViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteTestBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupMapProviderSelection()
        setupTravelModeSelection()
        setupCalculateButton()
        setupLongestRouteButton()
        observeViewModel()
    }
    
    /**
     * 设置地图提供商选择
     */
    private fun setupMapProviderSelection() {
        binding.mapProviderGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_amap -> viewModel.setMapProvider("amap")
                R.id.radio_bmap -> viewModel.setMapProvider("bmap")
            }
        }
    }
    
    /**
     * 设置交通方式选择
     */
    private fun setupTravelModeSelection() {
        binding.travelModeGroup.setOnCheckedChangeListener { _, checkedId ->
            val travelMode = when (checkedId) {
                R.id.radio_walking -> "walking"
                R.id.radio_driving -> "driving"
                R.id.radio_transit -> "transit"
                R.id.radio_riding -> "riding"
                else -> "driving"
            }
            viewModel.setTravelMode(travelMode)
        }
    }
    
    /**
     * 设置计算路线按钮
     */
    private fun setupCalculateButton() {
        binding.calculateButton.setOnClickListener {
            val originText = binding.originInput.text.toString().trim()
            val destinationText = binding.destinationInput.text.toString().trim()
            
            if (originText.isEmpty() || destinationText.isEmpty()) {
                Toast.makeText(requireContext(), R.string.origin_destination_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // 显示加载状态
            binding.progressBar.visibility = View.VISIBLE
            binding.resultCard.visibility = View.GONE
            
            // 计算路线
            lifecycleScope.launch {
                try {
                    viewModel.calculateRoute(originText, destinationText)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
    
    /**
     * 设置计算最长用时路线按钮
     */
    private fun setupLongestRouteButton() {
        binding.calculateLongestButton.setOnClickListener {
            val originText = binding.originInput.text.toString().trim()
            val destinationText = binding.destinationInput.text.toString().trim()
            
            if (originText.isEmpty() || destinationText.isEmpty()) {
                Toast.makeText(requireContext(), R.string.origin_destination_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // 显示加载状态
            binding.progressBar.visibility = View.VISIBLE
            binding.resultCard.visibility = View.GONE
            
            // 计算最长用时路线
            lifecycleScope.launch {
                try {
                    viewModel.calculateLongestRoute(originText, destinationText)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
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
        
        // 交通方式
        viewModel.travelMode.observe(viewLifecycleOwner) { mode ->
            val radioButton = when (mode) {
                "walking" -> binding.radioWalking
                "driving" -> binding.radioDriving
                "transit" -> binding.radioTransit
                "riding" -> binding.radioRiding
                else -> binding.radioDriving
            }
            radioButton.isChecked = true
        }
        
        // 路线计算结果
        viewModel.routeResult.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE
            
            if (result != null) {
                binding.resultCard.visibility = View.VISIBLE
                
                // 显示结果
                binding.distanceText.text = result.getFormattedDistance()
                binding.durationText.text = result.getFormattedDuration()
                binding.travelModeText.text = result.getLocalizedTravelMode()
                binding.summaryText.text = result.summary
                
                // 显示路径点数量
                binding.pathPointsText.text = getString(R.string.path_points_count, result.path.size)
            } else {
                binding.resultCard.visibility = View.GONE
                Toast.makeText(requireContext(), R.string.route_calculation_failed, Toast.LENGTH_SHORT).show()
            }
        }
        
        // 错误信息
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
