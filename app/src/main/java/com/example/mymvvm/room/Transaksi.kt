package com.example.mymvvm.room

data class PaymentIncomeList(
    val payment: String?= null,
    val x: String?= null,
    val allTotalPayment : String?= null,)



data class HeaderSales (
    val idTag: String?= null,
    val idTranx: String?= null,
    val allTotal : String?= null,
    val record : String?= null,
    val tanggal: String?= null,
    val jam: String?= null,
    val idCustomer: String?= null,
    val payment: String?= null,
    val ppn: String?= null,
    val charge: String?= null,
    val delete: String?=null,
)