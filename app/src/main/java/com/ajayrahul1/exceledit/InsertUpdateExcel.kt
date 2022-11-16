package com.ajayrahul1.exceledit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ajayrahul1.exceledit.databinding.ActivityInsertUpdateExcelBinding
import com.ajayrahul1.exceledit.databinding.ActivityMainBinding

class InsertUpdateExcel : AppCompatActivity() {
    private lateinit var modifyExcelBind: ActivityInsertUpdateExcelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modifyExcelBind = ActivityInsertUpdateExcelBinding.inflate(layoutInflater)
        setContentView(modifyExcelBind.root)


    }
}