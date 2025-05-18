package com.example.travelplanapp.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.travelplanapp.R
import com.example.travelplanapp.databinding.FragmentLanguageSettingsBinding
import com.example.travelplanapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 语言设置Fragment
 * 用于设置应用语言
 */
@AndroidEntryPoint
class LanguageSettingsFragment : Fragment() {

    private var _binding: FragmentLanguageSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupLanguageRadioGroup()
        setupSaveButton()
        observeViewModel()
    }
    
    /**
     * 设置语言单选按钮组
     */
    private fun setupLanguageRadioGroup() {
        // 根据当前语言设置选中状态
        viewModel.appLanguage.observe(viewLifecycleOwner) { language ->
            when (language) {
                "zh" -> binding.radioZh.isChecked = true
                "en" -> binding.radioEn.isChecked = true
                else -> binding.radioAuto.isChecked = true
            }
        }
    }
    
    /**
     * 设置保存按钮
     */
    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val selectedLanguage = when (binding.languageRadioGroup.checkedRadioButtonId) {
                R.id.radio_zh -> "zh"
                R.id.radio_en -> "en"
                else -> "auto"
            }
            
            viewModel.setAppLanguage(selectedLanguage)
        }
    }
    
    /**
     * 观察ViewModel数据变化
     */
    private fun observeViewModel() {
        viewModel.languageChangeStatus.observe(viewLifecycleOwner) { status ->
            if (status == true) {
                // 显示重启对话框
                showRestartDialog()
                viewModel.clearLanguageChangeStatus()
            }
        }
    }
    
    /**
     * 显示重启对话框
     */
    private fun showRestartDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.language_changed)
            .setMessage(R.string.language_changed)
            .setPositiveButton(R.string.restart_now) { _, _ ->
                // 重启应用
                restartApp()
            }
            .setNegativeButton(R.string.restart_later) { _, _ ->
                Toast.makeText(
                    requireContext(),
                    R.string.language_changed,
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }
    
    /**
     * 重启应用
     */
    private fun restartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
