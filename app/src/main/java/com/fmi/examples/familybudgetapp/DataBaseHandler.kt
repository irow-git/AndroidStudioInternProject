package com.fmi.examples.familybudgetapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "FamilyAppDB"
val TABLE_NAME="Budged"
val COL_ID = "id"
val COL_DAY = "day"
val COL_MONTH = "month"
val COL_AMOUNT = "amount"
val COL_NAME = "name"
val COL_ISINCOME = "isIncome"

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

    fun readData(): MutableList<DBStructure> {
        var list  : MutableList<DBStructure> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var dBStructure = DBStructure()
                dBStructure.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                dBStructure.day = result.getString(result.getColumnIndex(COL_DAY))
                dBStructure.month = result.getString(result.getColumnIndex(COL_MONTH))
                dBStructure.amount = result.getString(result.getColumnIndex(COL_AMOUNT)).toDouble()
                dBStructure.name = result.getString(result.getColumnIndex(COL_NAME))
                dBStructure.isIncome = result.getString(result.getColumnIndex(COL_ISINCOME)).toInt()
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
}