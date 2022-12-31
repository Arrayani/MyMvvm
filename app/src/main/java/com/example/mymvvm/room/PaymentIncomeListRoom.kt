package com.example.mymvvm.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentIncomeListRoom(
    @PrimaryKey(autoGenerate = false) val no :Int?,
    val payment: String? = null,
    val x: String? = null,
    val allTotalPayment: String? = null
)
