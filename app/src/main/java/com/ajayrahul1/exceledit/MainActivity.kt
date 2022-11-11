package com.ajayrahul1.exceledit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.ajayrahul1.exceledit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val items = listOf("Customers.xlsx", "Finance.xlsx", "Components.xlsx")
        val adapterItems = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.dropdownExcelOptions.setAdapter(adapterItems)
    }
}