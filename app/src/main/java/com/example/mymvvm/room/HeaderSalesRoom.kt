package com.example.mymvvm.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HeaderSalesRoom(
    @PrimaryKey(autoGenerate = false) val no :Int?,
    @ColumnInfo(name = "idTag") val idTag: String?= null,
    @ColumnInfo(name = "idTranx") val idTranx: String?= null,
    @ColumnInfo(name = "allTotal") val allTotal : String?= null,
    @ColumnInfo(name = "record") val record : String?= null,
    @ColumnInfo(name = "tanggal") val tanggal: String?= null,
    @ColumnInfo(name = "jam") val jam: String?= null,
    @ColumnInfo(name = "idCustomer") val idCustomer: String?= null,
    @ColumnInfo(name = "payment") val payment: String?= null,
    @ColumnInfo(name = "ppn") val ppn: String?= null,
    @ColumnInfo(name = "charge") val charge: String?= null,
    @ColumnInfo(name = "delete") val delete: String?=null,
)