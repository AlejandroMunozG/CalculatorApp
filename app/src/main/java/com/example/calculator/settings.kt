package com.example.calculator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.calculator.databinding.FragmentSettingsBinding
import android.content.SharedPreferences



class settings : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        binding =FragmentSettingsBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences(
            "MyAppPreferences",
            android.content.Context.MODE_PRIVATE
        )
        val darkThemeToggle = binding.darkModeSwitch

        // Check the current night mode and set the switch state accordingly
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        darkThemeToggle.isChecked = currentNightMode == AppCompatDelegate.MODE_NIGHT_YES

        darkThemeToggle.setOnCheckedChangeListener { _, isChecked ->
            // Set the night mode based on the switch state
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Save the night mode preference to persist it across app restarts
            saveNightModePreference(isChecked)
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    private fun saveNightModePreference(isNightModeEnabled: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "MyAppPreferences",
            android.content.Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean("night_mode_enabled", isNightModeEnabled)
        editor.apply()
    }
}