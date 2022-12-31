package com.example.mymvvm.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotaTelDao {
    /*** HeaderSaLes DAO **/
    @Query("SELECT * FROM HeaderSalesRoom")
    //ini versi mvvm nya header
    fun getAllHeaderSRoom(): LiveData<List<HeaderSalesRoom>>
    //cuma read aja yang ngga perlu suspend??

    @Query("SELECT * FROM PaymentIncomeListRoom")
    fun getAllPaymentIncomeListroom():LiveData<List<PaymentIncomeListRoom>>

    @Query("SELECT * FROM PengeluaranRoom")
    fun getAllPengeluaranRoom():LiveData<List<PengeluaranRoom>>

    @Query("SELECT * FROM TodayPengeluaranRoom")
    fun getTodayPengeluaranRoom():LiveData<List<TodayPengeluaranRoom>>

    @Query("SELECT * FROM TakeHomeMoneyRoom")
    fun getTakeHomeMoney():LiveData<List<TakeHomeMoneyRoom>>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    // suspend fun insertHeaderSRoom(allInCart: HeaderSalesRoom)
    suspend fun insertHeaderSRoom(allInCart: HeaderSalesRoom)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPengeluaranRoom(allPengeluaranRoom: PengeluaranRoom)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodayPengeluaranRoom(todayPengeluaranRoom: TodayPengeluaranRoom)

    @Delete
    suspend fun delOneHeaderSRoom(allInCart: HeaderSalesRoom)

    @Query("DELETE FROM HeaderSalesRoom")
    // suspend fun deleteAllHeaderSRoom()
    suspend fun deleteAllHeaderSRoom()

    @Query("DELETE FROM PengeluaranRoom")
    suspend fun dellAllPengeluaranRoom()

    @Query("DELETE FROM paymentincomelistroom")
    suspend fun deleteAllpaymentincomelistroom()

    @Query("DELETE FROM todaypengeluaranroom")
    suspend fun dellAllTodayPengeluaranRoom()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    // suspend fun insertHeaderSRoom(allInCart: HeaderSalesRoom)
    suspend fun insertAllPaymentIncomeListRoom(paymentIncomeListRoom: PaymentIncomeListRoom)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTakeHomeMoneyRoom(takeHomeMoneyRoom: ArrayList<TakeHomeMoneyRoom>)

    @Query("DELETE FROM takehomemoneyroom")
    suspend fun delAllTakeHomeMoneyRoom()

    @Query("UPDATE TakeHomeMoneyRoom SET sumTotal=:newValue WHERE id LIKE :id")
    suspend fun updateTakeHomeMoneyRoom(newValue:String,id:Int)
}