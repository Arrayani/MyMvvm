package com.example.mymvvm.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PengeluaranRoom (
    @PrimaryKey(autoGenerate = false) val no :Int?,
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