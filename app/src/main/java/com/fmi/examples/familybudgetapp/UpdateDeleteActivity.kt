package com.fmi.examples.familybudgetapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_delete.*
import kotlinx.android.synthetic.main.fragment_add_expense_screen.*
import kotlinx.android.synthetic.main.fragment_add_expense_screen.view.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateDeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        var intent1 = intent.getStringExtra("Name").toString()
        UDAmountEditTextID.setText(intent1)

        /**
         * Calendar
         */

        var monthcb = ""
        var bool = false
        UDPickDateIncomeBtn.setOnClickListener {
            val formate = SimpleDateFormat("dd MMM yyyy", Locale.UK)
            val now = Calendar.getInstance()
            val datePicker =
                context?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(Calendar.YEAR, year)
                            selectedDate.set(Calendar.MONTH, month)
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            val date = formate.format(selectedDate.time)
                            Toast.makeText(it1, "Дата: $date", Toast.LENGTH_SHORT).show()
                            dayTextViewExpense.text = ("Ден: $dayOfMonth")
                            if (bool)
                                monthTextViewExpense.text = ("Месец: $monthcb")
                            else {
                                monthcb = month.toString()
                                monthTextViewExpense.text = ("Месец: $month")
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                    )

                }
            datePicker?.show()
        }

            UDCheckBoxIncome.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                monthcb = "всички"
                bool = true
                monthTextViewExpense.text = ("Месец: $monthcb")
            } else if (!isChecked) {
                bool = false
            }
        }
    }
}