package com.fmi.examples.familybudgetapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_delete.*
import java.text.SimpleDateFormat
import java.util.*

var message = ""
class UpdateDeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        message = intent.getStringExtra("Name").toString()
        val string: String = message
        val listArrayMain: List<String> = string.split("\n")
        val listArraySub1: List<String> = listArrayMain[0].split(" ")
        val listArraySub2: List<String> = listArrayMain[1].split(" ")
        UDNameEditTextIncome.setText(listArraySub1[2])
        UDAmountEditTextID.setText(listArraySub2[1])
        UDDayTextViewID.text = listArrayMain[2]
        UDMonthTextViewID.text = listArrayMain[3]

        UDDeleteBtnID.setOnClickListener{
            var dialog = AlertDialog.Builder(this)
                .setTitle("Внимание!")
                .setMessage("Наистина ли искате да изтрието това!")
                .setPositiveButton("ДА", null)
                .setNegativeButton("Върни се", null)
                .show()
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener{
                val string: String = message
                val listArray: List<String> = string.split(":")
                val db = DataBaseHandler(this)
                db.deleteData(listArray[0])
                val intent = Intent(this, AlterDBScreenActivity::class.java)
                startActivity(intent)
            }
        }

        UDHomeScreenIncomeBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        UDChangeBtnID.setOnClickListener(){
            val string: String = message
            val listArray: List<String> = string.split(":")
            val db = DataBaseHandler(this)

            if(UDAmountEditTextID.text.isEmpty() || UDAmountEditTextID.text.equals(String)
                || UDNameEditTextIncome.text.isEmpty()
                || UDDayTextViewID.text.isEmpty()
                || UDMonthTextViewID.text.isEmpty())
            {
                Toast.makeText(this,"Грешно попълнени данни!", Toast.LENGTH_SHORT).show()
            }
            else {
                db.updateData(
                    UDAmountEditTextID.text.toString().toDouble(),
                    UDNameEditTextIncome.text.toString(),
                    UDDayTextViewID.text.toString(),
                    UDMonthTextViewID.text.toString(),
                    listArray[0]
                )
                val intent = Intent(this, AlterDBScreenActivity::class.java)
                startActivity(intent)
            }
        }

        /**
         * Calendar
         */

        var monthcb = ""
        var bool = false
        UDPickDateIncomeBtn.setOnClickListener {
            val formate = SimpleDateFormat("dd MMM yyyy", Locale.UK)
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)
                Toast.makeText(this, "Date: $date",Toast.LENGTH_SHORT ).show()
                UDDayTextViewID.text = ("Ден: $dayOfMonth")
                if (bool)
                    UDMonthTextViewID.text = ("Месец: $monthcb")
                else {
                    monthcb = month.toString()
                    UDMonthTextViewID.text = ("Месец: $month")
                }
            },

                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

        }

            UDCheckBoxIncome.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                monthcb = "всички"
                bool = true
                UDMonthTextViewID.text = ("Месец: $monthcb")
            } else if (!isChecked) {
                bool = false
            }
        }
    }
}