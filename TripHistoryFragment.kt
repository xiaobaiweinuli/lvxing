package com.example.travelplanapp.ui.history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplanapp.R
import com.example.travelplanapp.databinding.FragmentTripHistoryBinding
import com.example.travelplanapp.ui.history.adapter.TripHistoryAdapter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * 历史行程记录Fragment
 * 用于显示历史行程记录和统计数据
 */
@AndroidEntryPoint
class TripHistoryFragment : Fragment() {

    private var _binding: FragmentTripHistoryBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TripHistoryViewModel by viewModels()
    private lateinit var tripHistoryAdapter: TripHistoryAdapter
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupDateRangePickers()
        setupAddTripButton()
        observeViewModel()
    }
    
    /**
     * 设置RecyclerView
     */
    private fun setupRecyclerView() {
        tripHistoryAdapter = TripHistoryAdapter(
            onEditClick = { tripHistory ->
                // 打开编辑对话框
                TripHistoryEditDialog.newInstance(tripHistory.id)
                    .show(childFragmentManager, "edit_trip_dialog")
            },
            onDeleteClick = { tripHistory ->
                // 显示删除确认对话框
                showDeleteConfirmationDialog(tripHistory)
            }
        )
        
        binding.tripHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tripHistoryAdapter
        }
    }
    
    /**
     * 设置日期范围选择器
     */
    private fun setupDateRangePickers() {
        // 设置开始日期选择器
        binding.startDateInput.setOnClickListener {
            showDatePicker(isStartDate = true)
        }
        
        // 设置结束日期选择器
        binding.endDateInput.setOnClickListener {
            showDatePicker(isStartDate = false)
        }
        
        // 初始化日期显示
        viewModel.startDate.observe(viewLifecycleOwner) { date ->
            binding.startDateInput.setText(dateFormat.format(date))
        }
        
        viewModel.endDate.observe(viewLifecycleOwner) { date ->
            binding.endDateInput.setText(dateFormat.format(date))
        }
    }
    
    /**
     * 显示日期选择器
     */
    private fun showDatePicker(isStartDate: Boolean) {
        val calendar = Calendar.getInstance()
        val date = if (isStartDate) viewModel.startDate.value else viewModel.endDate.value
        date?.let {
            calendar.time = it
        }
        
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            val selectedDate = calendar.time
            
            if (isStartDate) {
                viewModel.setStartDate(selectedDate)
            } else {
                viewModel.setEndDate(selectedDate)
            }
        }, year, month, day).show()
    }
    
    /**
     * 设置添加行程按钮
     */
    private fun setupAddTripButton() {
        binding.addTripFab.setOnClickListener {
            // 打开添加行程对话框
            TripHistoryEditDialog.newInstance()
                .show(childFragmentManager, "add_trip_dialog")
        }
    }
    
    /**
     * 观察ViewModel数据变化
     */
    private fun observeViewModel() {
        // 观察行程列表
        viewModel.filteredTripHistories.observe(viewLifecycleOwner) { tripHistories ->
            tripHistoryAdapter.submitList(tripHistories)
            
            // 显示空视图或列表
            if (tripHistories.isEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
                binding.tripHistoryRecyclerView.visibility = View.GONE
            } else {
                binding.emptyView.visibility = View.GONE
                binding.tripHistoryRecyclerView.visibility = View.VISIBLE
            }
        }
        
        // 观察统计数据
        viewModel.tripStatistics.observe(viewLifecycleOwner) { statistics ->
            updateStatisticsUI(statistics)
        }
        
        // 观察操作状态
        viewModel.operationStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    is TripHistoryViewModel.OperationStatus.Success -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is TripHistoryViewModel.OperationStatus.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.clearOperationStatus()
            }
        }
    }
    
    /**
     * 更新统计UI
     */
    private fun updateStatisticsUI(statistics: TripStatistics) {
        // 更新基本统计数据
        binding.totalTripsCount.text = statistics.totalTrips.toString()
        binding.onTimeTripsCount.text = statistics.onTimeTrips.toString()
        binding.delayedTripsCount.text = statistics.delayedTrips.toString()
        
        // 更新准时率
        val onTimeRate = statistics.getOnTimeRate()
        binding.onTimeRateProgress.progress = onTimeRate.toInt()
        binding.onTimeRateText.text = String.format("%.1f%%", onTimeRate)
        
        // 更新平均用时
        binding.averageDurationText.text = statistics.getFormattedAverageDuration()
        
        // 更新交通方式分布图表
        updateTravelModeChart(statistics.tripsByTravelMode)
        
        // 更新月度趋势图表
        updateMonthlyTrendChart(statistics.tripsByMonth)
    }
    
    /**
     * 更新交通方式分布图表
     */
    private fun updateTravelModeChart(tripsByTravelMode: Map<String, Int>) {
        // 创建饼图
        val pieChart = PieChart(requireContext())
        binding.travelModeChartContainer.removeAllViews()
        binding.travelModeChartContainer.addView(pieChart)
        
        // 准备数据
        val entries = ArrayList<PieEntry>()
        val travelModeNames = mapOf(
            "walking" to getString(R.string.walking),
            "driving" to getString(R.string.driving),
            "transit" to getString(R.string.transit),
            "riding" to getString(R.string.riding)
        )
        
        tripsByTravelMode.forEach { (mode, count) ->
            val modeName = travelModeNames[mode] ?: mode
            entries.add(PieEntry(count.toFloat(), modeName))
        }
        
        // 设置数据集
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f
        
        // 设置图表数据
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        
        // 配置图表
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.legend.textSize = 12f
        pieChart.centerText = getString(R.string.travel_mode)
        pieChart.setCenterTextSize(16f)
        
        // 刷新图表
        pieChart.invalidate()
    }
    
    /**
     * 更新月度趋势图表
     */
    private fun updateMonthlyTrendChart(tripsByMonth: Map<String, Int>) {
        // 创建柱状图
        val barChart = BarChart(requireContext())
        binding.monthlyTrendChartContainer.removeAllViews()
        binding.monthlyTrendChartContainer.addView(barChart)
        
        // 准备数据
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        
        tripsByMonth.entries.forEachIndexed { index, entry ->
            entries.add(BarEntry(index.toFloat(), entry.value.toFloat()))
            labels.add(entry.key)
        }
        
        // 设置数据集
        val dataSet = BarDataSet(entries, getString(R.string.trip_count))
        dataSet.color = resources.getColor(R.color.colorPrimary, null)
        dataSet.valueTextSize = 12f
        
        // 设置图表数据
        val barData = BarData(dataSet)
        barChart.data = barData
        
        // 配置X轴
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = -45f
        
        // 配置图表
        barChart.description.isEnabled = false
        barChart.legend.textSize = 12f
        barChart.setFitBars(true)
        barChart.animateY(1000)
        
        // 刷新图表
        barChart.invalidate()
    }
    
    /**
     * 显示删除确认对话框
     */
    private fun showDeleteConfirmationDialog(tripHistory: com.example.travelplanapp.data.model.TripHistory) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_trip)
            .setMessage(R.string.delete_trip_confirmation)
            .setPositiveButton(R.string.confirm) { _, _ ->
                viewModel.deleteTripHistory(tripHistory)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
