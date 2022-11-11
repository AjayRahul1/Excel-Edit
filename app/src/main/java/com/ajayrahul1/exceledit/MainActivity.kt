package com.ajayrahul1.exceledit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.makeText
import com.ajayrahul1.exceledit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()    // Hides the action bar

        val items = listOf("Customers.xlsx", "Finance.xlsx", "Components.xlsx")
        val adapterItems = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.DropDownExcelFiles.setAdapter(adapterItems)

        binding.DropDownExcelFiles.setOnItemClickListener { parent, view, position, id ->
            makeText(
                applicationContext,
                parent.getItemAtPosition(position).toString() + " file deleted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}