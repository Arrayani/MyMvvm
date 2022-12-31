package com.example.mymvvm.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TakeHomeMoneyRoom(
    @PrimaryKey(autoGenerate = false) val id :Int?,
    val sumTotal:String? =null
)

//@PrimaryKey(autoGenerate = false) val no :Int?,
// kalimat no itu udah salah satu library nya sql, jangan gunakan no sebagai
// nama sebuah kolom, bakal error!!!