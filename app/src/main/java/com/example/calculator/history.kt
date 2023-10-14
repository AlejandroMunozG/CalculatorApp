package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculator.databinding.FragmentHistoryBinding
import android.content.Intent
import android.util.Log


class history : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)

        val calcList = arguments?.getStringArrayList("calcList")
        val historyText = calcList?.joinToString("\n")
        binding.historyTextView.text = historyText

        return view
    }

}