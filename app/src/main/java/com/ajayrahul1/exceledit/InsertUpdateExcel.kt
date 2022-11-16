package com.ajayrahul1.exceledit

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ajayrahul1.exceledit.databinding.ActivityInsertUpdateExcelBinding

class InsertUpdateExcel : AppCompatActivity() {
    private lateinit var modifyExcelBind: ActivityInsertUpdateExcelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modifyExcelBind = ActivityInsertUpdateExcelBinding.inflate(layoutInflater)
        setContentView(modifyExcelBind.root)

        modifyExcelBind.noOfColumnsCount.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    // Function that closes keyboard when clicked ENTER KEY
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}