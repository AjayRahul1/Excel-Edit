package com.ajayrahul1.exceledit

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.ajayrahul1.exceledit.databinding.ActivityMainBinding
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    /*companion object {
        const val dataReceived = "Sample"
    }*/

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED
        )
        changeParametersOfElements()

        supportActionBar?.hide()    // Hides the action bar

        /* checkPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE", STORAGE_PERMISSION_CODE)
        checkPermission("Manifest.permission.READ_EXTERNAL_STORAGE", STORAGE_PERMISSION_CODE) */

        binding.txtExistingExcel.setOnClickListener {
            startActivity(Intent(this, InsertUpdateExcel::class.java))
        }

        val items = mutableListOf<String>()
        setItemsToDropDownMenu(items)

        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val dataReceived = handleSendText(intent)
            makeText(
                this,
                "Enter the name of the excel to create excel with Data",
                Toast.LENGTH_LONG
            ).show()
            createExcel(items, dataReceived)
        }

        binding.DropDownExcelFiles.setOnItemClickListener { parent, _, position, _ ->
            makeText(
                applicationContext,
                "${parent.getItemAtPosition(position)} file selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnCreateExcel.setOnClickListener {
            createExcel(items, dataReceived = "")
        }

        binding.btnModifyExcel.setOnClickListener {
            startActivity(Intent(applicationContext, InsertUpdateExcel::class.java))
        }

        binding.btnDeleteExcel.setOnClickListener { deleteExcel(items) }

        binding.txtClickableKnowAbtUs.setOnClickListener { knowAboutUs() }
    }

    private fun handleSendText(intent: Intent) = intent.getStringExtra(Intent.EXTRA_TEXT).toString()

    private fun knowAboutUs() {
        if (binding.txtClickableKnowAbtUs.text == getString(R.string.txt_clickable_know_about_us)) {
            binding.txtClickableKnowAbtUs.text = getString(R.string.know_about_us_desc)
            binding.txtClickableKnowAbtUs.setCompoundDrawables(null, null, null, null)
        } else {
            binding.txtClickableKnowAbtUs.text = getString(R.string.txt_clickable_know_about_us)
            binding.txtClickableKnowAbtUs.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_info,
                0,
                0,
                0
            )
        }
    }

    /* // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }*/
    // Commented it because permission not required


    private fun setVisibilityOfCreateExcelInput(visibilityValue: Int): Boolean {
        return when (visibilityValue) {
            0 -> {
                binding.etCreateExcelInput.visibility = View.INVISIBLE
                binding.createExcelInputOutline.visibility = View.INVISIBLE
                false
            }
            else -> {
                binding.etCreateExcelInput.visibility = View.VISIBLE
                binding.createExcelInputOutline.visibility = View.VISIBLE
                true
            }
        }
    }

    private fun createExcel(items: MutableList<String>, dataReceived: String) {
        setVisibilityOfCreateExcelInput(1)
        changeParametersOfElements()
        binding.etCreateExcelInput.setOnKeyListener { view, keyCode, _ ->
            val directory =
                File("${Environment.getExternalStorageDirectory()}/Documents/Excel Edit")
            if (!directory.exists())
                if (directory.mkdir()) Log.e(
                    "Create Dir",
                    "Directory created successfully"
                ) // Create directory if it doesn't exist

            // Creating an Excel File Workbook
            val newWorkbookExcel = HSSFWorkbook()

            // Creating a sheet inside Workbook
            val excelSheet = newWorkbookExcel.createSheet("Sheet 1")

            // Set the row to edit
            val excelRowEditing = excelSheet.createRow(0)

            // Set the column to edit
            val excelColumnEditing = excelRowEditing.createCell(0)

            // Providing the text to enter into excel
            excelColumnEditing.setCellValue(dataReceived)

            // Setting a path where excel should be saved in storage
            val filepath =
                File("${Environment.getExternalStorageDirectory()}/Documents/Excel Edit/${binding.etCreateExcelInput.text.toString()}.xlsx")

            // If file doesn't exist, create it
            try {
                if (!filepath.exists()) {
                    filepath.createNewFile()
                }

                // creating output stream to write data
                val fileOutputStream = FileOutputStream(filepath)

                // Writing into excel file from output stream
                newWorkbookExcel.write(fileOutputStream)

                fileOutputStream.flush()
                fileOutputStream.close() // Closing fileOutputStream
                makeText(
                    this,
                    "${binding.etCreateExcelInput.text.toString()}.xlsx created in Documents Folder",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            items.add(binding.etCreateExcelInput.text.toString() + ".xlsx")
            binding.etCreateExcelInput.text?.clear()
            if (items.isNotEmpty())
                binding.DropDownExcelFiles.setText(getString(R.string.select_another_file))
            setItemsToDropDownMenu(items)

            handleKeyEvent(view, keyCode)
        }
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }*/

    private fun setItemsToDropDownMenu(items: MutableList<String>) {
        if (items.isEmpty())
            binding.DropDownExcelFiles.setText(getString(R.string.no_files_remaining))
        val adapterItems = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.DropDownExcelFiles.setAdapter(adapterItems)
    }

    private fun deleteExcel(items: MutableList<String>) {
        val fileToDelete =
            File("${Environment.getExternalStorageDirectory()}/Documents/Excel Edit/${binding.DropDownExcelFiles.text}").delete()
        try {
            if (fileToDelete) {
                Log.e("Deletion of Excel", "File Deleted")
                makeText(this, "${binding.DropDownExcelFiles.text} deleted 🎉",Toast.LENGTH_SHORT).show()
            } else Log.e("Deletion Of Excel", "Deletion unsuccessful")
        } catch (e: Exception) {
            e.printStackTrace()
            makeText(this, "${binding.DropDownExcelFiles.text} deletion unsuccessful",Toast.LENGTH_SHORT).show()
        }
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
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)


            // setting visibility to invisible when enter key is pressed to make it disappear
            setVisibilityOfCreateExcelInput(0) // created method not built-in

            changeParametersOfElements()
            return true
        }
        return false
    }

    private fun changeParametersOfElements() {
        val params = binding.txtExistingExcel.layoutParams as ConstraintLayout.LayoutParams
        if (binding.etCreateExcelInput.visibility == View.INVISIBLE &&
            binding.createExcelInputOutline.visibility == View.INVISIBLE
        ) {

            params.startToStart = binding.btnCreateExcel.id
            params.topToBottom = binding.btnCreateExcel.id

        }
        if (binding.etCreateExcelInput.visibility == View.VISIBLE &&
            binding.createExcelInputOutline.visibility == View.VISIBLE
        ) {

            params.startToStart = binding.btnCreateExcel.id
            params.topToBottom = binding.createExcelInputOutline.id
        }
        binding.txtExistingExcel.requestLayout()
    }
}