package com.fmi.examples.familybudgetapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_update_delete.*

import kotlinx.android.synthetic.main.fragment_add_income_screen.*
import kotlinx.android.synthetic.main.fragment_add_income_screen.view.*
import java.text.SimpleDateFormat
import java.util.*


class AddIncomeScreen : Fragment() {

    var intForDb = 1
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_income_screen, container, false)

        view.homeScreenIncomeBtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_addIncomeScreen_to_mainScreen) }
        view.alterDBIncomeBtn.setOnClickListener {
            val intent = Intent(activity, AlterDBScreenActivity::class.java)
            startActivity(intent)
        }

        view.addIncomeButton.setOnClickListener{

            if(UDAmountEditTextID.text.isEmpty() || UDAmountEditTextID.text.equals(String)
                || UDNameEditTextIncome.text.isEmpty()
                || UDDayTextViewID.text.isEmpty()
                || UDMonthTextViewID.text.isEmpty())
            {
                Toast.makeText(context,"Грешно попълнени данни!", Toast.LENGTH_SHORT).show()
            }
            else
            data()

        }


        /**
         * Calendar
         */

        var monthcb = ""
        var bool = false
        view.pickDateIncomeBtn.setOnClickListener {
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
                            dayTextViewIncome.text = ("Ден: $dayOfMonth")
                            if (bool)
                                monthTextViewIncome.text = ("Месец: $monthcb")
                            else {
                                monthcb = month.toString()
                                monthTextViewIncome.text = ("Месец: $month")
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                    )

                }
            datePicker?.show()
        }

        view.checkBoxIncome.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                monthcb = "всички"
                bool = true
                monthTextViewIncome.text = ("Месец: $monthcb")
            } else if (!isChecked) {
                bool = false
            }
        }

        return view
    }
    private fun data() {
        val dbStructure = DBStructure(
            dayTextViewIncome.text.toString(),
            monthTextViewIncome.text.toString(),
            AmountEditTextIncome.text.toString().toDouble(),
            NameEditTextIncome.text.toString(),
            intForDb
        )
        val db = context?.let { it1 -> DataBaseHandler(it1) }
        db?.insertData(dbStructure)
    }
}
