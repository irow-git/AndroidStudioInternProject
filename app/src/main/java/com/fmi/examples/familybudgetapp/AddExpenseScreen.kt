package com.fmi.examples.familybudgetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_expense_screen.view.*

class AddExpenseScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_expense_screen, container, false)

        view.homeScreenExpenseBtn.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_addExpenseScreen_to_mainScreen)}
        view.alterDBExpenseBtn.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_addExpenseScreen_to_alterDBScreen)}

        return view
    }
}