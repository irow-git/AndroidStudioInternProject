package com.fmi.examples.familybudgetapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_income_screen.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var formate = SimpleDateFormat( "dd MMM, yyyy", Locale.GERMANY)

    //internal means a variable is visible within a given module
    internal var dbHelper = DatebaseHelper(this)

    /**
     * Creating a function to show Toast message
     */
    fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title : String, Message : String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /**
     *Creating a method to clear our editTexts
     */

        fun clearEditTexts() {
        AmountIncomeEditTxt.setText("")
        fragIncomeNameEditTxt.setText("")
        dateText.text = ""
    }


    fun handleInserts() {
        alterDBIncomeBtn.setOnClickListener {
            try {
                dbHelper.insertData(AmountIncomeEditTxt.text.toString(),
                    fragIncomeNameEditTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleUpdates() {
        alterDBIncomeBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(AmountIncomeEditTxt.text.toString(),
                    fragIncomeNameEditTxt.text.toString())
                if (isUpdate == true)
                    showToast("Date Updated Successfully")
                else
                    showToast("Data Not Updated")
            }catch (e:Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleDelets() {
        fragIncomeDeleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(fragIncomeNameEditTxt.text.toString())
                clearEditTexts()
            }catch (e:Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleViewling() {
        showIncomeListBtn.setOnClickListener {
            View.OnClickListener {
                val res = dbHelper.allData
                if(res.count == 0 ) {
                    showDialog("Error", "No Data Found")
                    return@OnClickListener
                }

                val buffer = StringBuffer()
                while (res.moveToNext()) {
                    buffer.append("Income:" + res.getString(2) + "\n")
                    buffer.append("Name:" + res.getString(1) + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleInserts()
        handleUpdates()
        handleDelets()
        handleViewling()


        /**
         * Creating a calendar
         */
        addIncBtn.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)
                Toast.makeText(this, "Date: " + date,Toast.LENGTH_SHORT ).show()
            },

                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
    }
}
}