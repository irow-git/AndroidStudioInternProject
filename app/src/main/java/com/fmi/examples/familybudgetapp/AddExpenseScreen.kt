package com.fmi.examples.familybudgetapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_alter_d_b_screen.*
import kotlinx.android.synthetic.main.fragment_add_expense_screen.*
import kotlinx.android.synthetic.main.fragment_add_expense_screen.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseScreen : Fragment() {

    var intForDb = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_expense_screen, container, false)

        view.homeScreenExpenseBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addExpenseScreen_to_mainScreen)
        }
        view.alterDBExpenseBtn.setOnClickListener {
            val intent = Intent(activity, AlterDBScreenActivity::class.java)
            startActivity(intent)
        }

        view.addExpenseButton.setOnClickListener {
            data()
        }


        /**
         * Calendar
         */

        var monthcb = ""
        var bool = false
        view.pickDateExpenseBtn.setOnClickListener {
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

        view.checkBoxExpense.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                monthcb = "всички"
                bool = true
                monthTextViewExpense.text = ("Месец: $monthcb")
            } else if (!isChecked) {
                bool = false
            }
        }
        return view
    }

    private fun data() {
        val dbStructure = DBStructure(
            dayTextViewExpense.text.toString(),
            monthTextViewExpense.text.toString(),
            AmountEditTextExpense.text.toString().toDouble(),
            NameEditTextExpense.text.toString(),
            intForDb
        )
        val db = context?.let { it1 -> DataBaseHandler(it1) }
        db?.insertData(dbStructure)
    }
}