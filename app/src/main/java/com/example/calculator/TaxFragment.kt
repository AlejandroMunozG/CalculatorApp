package com.example.calculator

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculator.databinding.FragmentTaxBinding
@Suppress("DEPRECATION")
class TaxFragment : Fragment(){
    private lateinit var binding: FragmentTaxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tax, container, false)

        binding = FragmentTaxBinding.bind(view)

        binding.taxEnterBtn.setOnClickListener {
            taxEnter()
        }

        return view
    }

    fun taxEnter() {
        val TAX = binding.taxInput.text.toString()
        binding.taxView.text = "Tax:"+TAX+"%"

        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("TAX",TAX)
        startActivity(intent)
    }

}