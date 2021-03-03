package com.fmi.examples.familybudgetapp

class DBStructure{

    var id : Int = 1
    var day : String = ""
    var month: String = ""
    var amount: Int = 0
    var name: String = ""
    var isIncome: Int = 0

    constructor(day: String, month: String, amount: Int, name: String, isIncome: Int){
        this.day = day
        this.month = month
        this.amount = amount
        this.name = name
        this.isIncome = isIncome
    }
}