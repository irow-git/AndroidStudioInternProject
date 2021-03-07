package com.fmi.examples.familybudgetapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_expense_screen.*
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_main_screen.view.*

class MainScreen : Fragment() {


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)

        view.addIncomeBtn.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_mainScreen_to_addIncomeScreen)}
        view.addExpenseButton.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_mainScreen_to_addExpenseScreen)}


        val temp = activity as MainActivity
        view.budgetTextView.text = temp.budged.toString() + " лв."


        return view
    }

}

