package com.fmi.examples.familybudgetapp

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.*

import kotlinx.android.synthetic.main.fragment_add_income_screen.*
import kotlinx.android.synthetic.main.fragment_add_income_screen.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class AddIncomeScreen : Fragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_income_screen, container, false)

        view.homeScreenIncomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addIncomeScreen_to_mainScreen)
        }
        view.alterDBIncomeBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_addIncomeScreen_to_alterDBScreen)
        }

        /**
         * Calendar
         */

        var monthcb = ""
        var bool = false
        view.pickDateIncomeBtn.setOnClickListener {
            val formate = SimpleDateFormat("dd MMM yyyy", Locale.GERMANY)
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

        view.checkBoxIncome.setOnCheckedChangeListener() { _, isChecked ->
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
}
