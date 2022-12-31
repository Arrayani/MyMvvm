package com.example.mymvvm.models

/**

 * Author: Roni Reynal Fitri  on $ {DATE} $ {HOUR}: $ {MINUTE}

 * Email: biruprinting@gmail.com

 */
data class Pengeluaran(
    val idTag: String?= null,
    val idThis: String?= null,
    val varianPengeluaran : String?=null,
    val jmlPengeluaran :  String?=null,
    val payment: String?= null,
    val tanggal: String?= null,
    val jam: String?= null,
   // val allTotal : String?= null,
    val record : String?= null,
    val delete: String?=null,
)
data class VarianPengeluaran(
    val varianPengeluaran : String?=null,
    val idThis : String?=null,
)
data class PaymentPengeluaran(
    val payment: String?= null,
    val idThis : String?=null,
)
//yang ini ga di buat di firebase, tapi di create di device tabelnya
data class HeaderPengeluaran(
    val tanggal: String?= null,
    val totalPengeluaran :String?= null,
    val cutPenjualan : String?=null,
)
