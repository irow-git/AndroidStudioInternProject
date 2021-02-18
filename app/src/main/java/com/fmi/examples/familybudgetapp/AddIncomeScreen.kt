package com.fmi.examples.familybudgetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_income_screen.view.*

class AddIncomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_income_screen, container, false)

        view.homeScreenIncomeBtn.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_addIncomeScreen_to_mainScreen)}
        view.alterDBIncomeBtn.setOnClickListener{Navigation.findNavController(view).navigate(R.id.action_addIncomeScreen_to_alterDBScreen)}

        return view
    }
}