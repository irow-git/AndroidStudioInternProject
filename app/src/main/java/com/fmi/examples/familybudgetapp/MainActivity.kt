package com.fmi.examples.familybudgetapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_alter_d_b_screen.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_income_screen.*
import kotlinx.android.synthetic.main.fragment_main_screen.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    var budged = 0.0;
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = DataBaseHandler(this)
        val data = db.readData()
        for(i in 0 until data.size) {
            if(data[i].isIncome == 1){
                budged += if (data[i].month.contains("всички")) {
                    (data[i].amount * 12)
                } else{
                    data[i].amount
                }
            }
            if(data[i].isIncome == 0){
                budged -= if (data[i].month.contains("всички")) {
                    (data[i].amount * 12)
                } else{
                    data[i].amount
                }
            }
        }
    }
}