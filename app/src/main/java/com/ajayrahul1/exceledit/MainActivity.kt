package com.ajayrahul1.exceledit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
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

        binding.btnCreateExcel.setOnClickListener {
            createExcel(items)
        }

        binding.btnModifyExcel.setOnClickListener {
            startActivity(Intent(applicationContext, InsertUpdateExcel::class.java))
        }

        binding.btnDeleteExcel.setOnClickListener {
            deleteExcel(items)
        }
    }

    private fun setVisibilityOfCreateExcelInput(visibilityValue: Int) {
        when (visibilityValue) {
            0 -> {
                binding.etCreateExcelInput.visibility = View.INVISIBLE
                binding.createExcelInputOutline.visibility = View.INVISIBLE
            }
            else -> {
                binding.etCreateExcelInput.visibility = View.VISIBLE
                binding.createExcelInputOutline.visibility = View.VISIBLE
            }
        }
    }

    private fun createExcel(items: MutableList<String>) {
        setVisibilityOfCreateExcelInput(1)
        binding.etCreateExcelInput.setOnKeyListener { view, keyCode, _ ->
            items.add(binding.etCreateExcelInput.text.toString()+".xlsx")
            binding.etCreateExcelInput.text?.clear()
            if (items.isNotEmpty())
                binding.DropDownExcelFiles.setText(getString(R.string.select_another_file))
            setItemsToDropDownMenu(items)
            handleKeyEvent(view, keyCode)
        }
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
            binding.DropDownExcelFiles.setText(getString(R.string.no_files_remaining))
        else if (!items.contains(binding.DropDownExcelFiles.text.toString())) {
            binding.DropDownExcelFiles.setText(getString(R.string.select_another_file))
            setItemsToDropDownMenu(items)
        }
    }

    // Function that closes keyboard when clicked ENTER KEY
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            // setting visibility to invisible when enter key is pressed to make it disappear
            setVisibilityOfCreateExcelInput(0)
            return true
        }
        return false
    }
}