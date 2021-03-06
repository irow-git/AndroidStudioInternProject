package com.fmi.examples.familybudgetapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_alter_d_b_screen.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_income_screen.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var budged = 0.0;
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = Bundle()
        val myMessage = "Stack Overflow is cool!"
        bundle.putString("message", myMessage)
        val fragInfo = MainScreen()
        fragInfo.arguments = bundle
        setContentView(R.layout.activity_main)
        val db = DataBaseHandler(this)
        val data = db.readData()
        for(i in 0 until data.size) {
            if(data[i].isIncome == 1){
                budged += data[i].amount;
            }
            if(data[i].isIncome == 0){
                budged -= data[i].amount;
            }
        }
    }
}