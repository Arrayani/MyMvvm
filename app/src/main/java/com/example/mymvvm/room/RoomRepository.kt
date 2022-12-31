package com.example.mymvvm.room

class RoomRepository(private val dao: NotaTelDao) {

    val repoGetTakeHomeMoney = dao.getTakeHomeMoney()

    //sepertinys repository hanya mengulang yang ada di NotatelDao
    val repoGetAllHeaderSroom =dao.getAllHeaderSRoom()
    val repoGetAllPaymentIncomeListroom = dao.getAllPaymentIncomeListroom()
    val repoGetAllPengeluaranRoom = dao.getAllPengeluaranRoom()
    val repoGetTodayPengeluaranRoom = dao.getTodayPengeluaranRoom()

    suspend fun insertRepHeadersRoom(allInCart: HeaderSalesRoom){

        dao.insertHeaderSRoom(allInCart)
    }
    suspend fun repoInsertPengeluaranRoom(allPengArrayList:PengeluaranRoom){
        dao.insertPengeluaranRoom(allPengArrayList)
    }
    suspend fun repoInsertTodayPengeluaranRoom(todayPengeluaranRoom: TodayPengeluaranRoom){
        dao.insertTodayPengeluaranRoom(todayPengeluaranRoom)
    }

    suspend fun delOneHeadersRepRoom(allInCart: HeaderSalesRoom){
        dao.delOneHeaderSRoom(allInCart)
    }
    suspend fun delAllHeadersRepRoom(){
        dao.deleteAllHeaderSRoom()
    }
    suspend fun repoDellAllPengeluaranRoom(){
        dao.dellAllPengeluaranRoom()
    }

    suspend fun delAllPaymentIncomeListRepRoom(){
        dao.deleteAllpaymentincomelistroom()
    }

    suspend fun repoDellAllTodayPengeluaranRoom(){
        dao.dellAllTodayPengeluaranRoom()
    }
    suspend fun repoDelAllTakeHomeMoneyRoom(){
        dao.delAllTakeHomeMoneyRoom()
    }

    suspend fun repoUpdateTakeHomeMoneyRoom(newValue:String,id:Int){
        dao.updateTakeHomeMoneyRoom(newValue,id)
    }

    suspend fun insertAllPaymentIncomeListRepRoom(paymentIncomeListRoom: PaymentIncomeListRoom){

        dao.insertAllPaymentIncomeListRoom(paymentIncomeListRoom)
    }

    suspend fun repoInsertTakeHomeMoneyRoom(inputTotaltransaksi: ArrayList<TakeHomeMoneyRoom>) {
        dao.insertTakeHomeMoneyRoom(inputTotaltransaksi)
    }

}
