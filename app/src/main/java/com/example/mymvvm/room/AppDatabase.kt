package com.example.mymvvm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [BarangCartRoom::class, HeaderSalesRoom::class],version = 1)
@Database(entities = [
    HeaderSalesRoom::class,
    PaymentIncomeListRoom::class,
    PengeluaranRoom::class,
    TodayPengeluaranRoom::class,
    TakeHomeMoneyRoom::class],
    version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    //abstract val notaTelDao : NotaTelDao

    abstract fun notaTelDao() : NotaTelDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "NotaTelDb"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}