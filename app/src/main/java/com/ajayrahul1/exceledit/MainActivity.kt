package com.ajayrahul1.exceledit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        val items = mutableListOf("Customers.xlsx", "Finance.xlsx", "Components.xlsx")
        setItemsToDropDownMenu(items)

        binding.DropDownExcelFiles.setOnItemClickListener { parent, _, position, _ ->
            makeText(
                applicationContext,
                "${parent.getItemAtPosition(position)} file selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnCreateExcel.setOnClickListener{
            createExcel(items)
        }

        binding.btnDeleteExcel.setOnClickListener {
            deleteExcel(items)
        }
    }

    private fun createExcel(items: MutableList<String>) {
        binding.createExcelInputOutline.visibility =  View.VISIBLE
        binding.etCreateExcelInput.visibility = View.VISIBLE
    }

    private fun setItemsToDropDownMenu(items: MutableList<String>) {
        val adapterItems = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.DropDownExcelFiles.setAdapter(adapterItems)
    }

    private fun deleteExcel(items: MutableList<String>) {
        makeText(
            applicationContext,
            "${binding.DropDownExcelFiles.text} file deleted",
            Toast.LENGTH_SHORT
        ).show()
        items.remove(binding.DropDownExcelFiles.text.toString())
        if (items.isEmpty())
            binding.DropDownExcelFiles.setText("No files left")
        else if (!items.contains(binding.DropDownExcelFiles.text.toString())) {
            binding.DropDownExcelFiles.setText("Select another one")
            setItemsToDropDownMenu(items)
        }
    }
}