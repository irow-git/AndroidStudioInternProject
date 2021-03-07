package com.fmi.examples.familybudgetapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alter_d_b_screen.*
import java.util.*


class AlterDBScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter_d_b_screen)
        val db = DataBaseHandler(this)
        val data = db.readData()
        val arrayList = ArrayList<String>()
        var arrayAdapter: ArrayAdapter<String>  = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            arrayList
        )
        for(i in 0 until data.size) {
            var s = ""
            s = if(data[i].isIncome == 0) "Приход"
            else "Разход"
            arrayList.add(data[i].id.toString() + ": Име: " + data[i].name + "\n"
                    + s + ": " + data[i].amount + "лв" +"\n"
                    + data[i].day + "\n" + data[i].month);
        }
        val listView = findViewById<ListView>(R.id.listView1);

        listView.adapter = arrayAdapter;

        listView.setOnItemClickListener { _, _, position, _ ->
            val name = arrayAdapter.getItem(position).toString()
            Toast.makeText(this, name, Toast.LENGTH_LONG).show()
        }

        homeScreenAlterDBBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}