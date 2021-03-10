package com.fmi.examples.familybudgetapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Toast

const val DATABASE_NAME = "FamilyAppDB"
const val TABLE_NAME="Budged"
const val COL_ID = "id"
const val COL_DAY = "day"
const val COL_MONTH = "month"
const val COL_AMOUNT = "amount"
const val COL_NAME = "name"
const val COL_ISINCOME = "isIncome"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_DAY + " VARCHAR(256)," +
                COL_MONTH + " VARCHAR(256)," +
                COL_AMOUNT + " DOUBLE," +
                COL_NAME + " VARCHAR(256)," +
                COL_ISINCOME + " BIT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(dbStructure: DBStructure) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_DAY, dbStructure.day)
        cv.put(COL_MONTH, dbStructure.month)
        cv.put(COL_AMOUNT, dbStructure.amount)
        cv.put(COL_NAME, dbStructure.name)
        cv.put(COL_ISINCOME, dbStructure.isIncome)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Грешка!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Информацията е запаметена", Toast.LENGTH_SHORT).show()
    }

    fun readData(): ArrayList<DBStructure> {
        var list  : ArrayList<DBStructure> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val dBStructure = DBStructure()
                dBStructure.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                dBStructure.day = result.getString(result.getColumnIndex(COL_DAY))
                dBStructure.month = result.getString(result.getColumnIndex(COL_MONTH))
                dBStructure.amount = result.getString(result.getColumnIndex(COL_AMOUNT)).toDouble()
                dBStructure.name = result.getString(result.getColumnIndex(COL_NAME))
                dBStructure.isIncome = result.getString(result.getColumnIndex(COL_ISINCOME)).toInt()
                list.add(dBStructure)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData(value: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(value))
        db.close()
    }
}