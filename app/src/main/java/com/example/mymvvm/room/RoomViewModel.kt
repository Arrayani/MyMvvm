package com.example.mymvvm.room

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymvvm.models.Pengeluaran
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RoomViewModel(private val repository: RoomRepository) : ViewModel(), Observable {
    val vmRepoGetTakeHomeMoney = repository.repoGetTakeHomeMoney

    //vmRepoGetAllPengeluaranRoom
   // val vmGetAllRepoHeaderSroom = repository.getAllRepoHeadersRoom
    val vmRepoGetAllHeaderSroom = repository.repoGetAllHeaderSroom

    //val vmGetAllRepoPaymentIncomeListroom = repository.getAllRepoPaymentIncomeListroom

    val vmRepoGetAllPaymentIncomeListroom = repository.repoGetAllPaymentIncomeListroom

    val vmRepoGetAllPengeluaranRoom = repository.repoGetAllPengeluaranRoom

    val vmRepoGetTodayPengeluaranRoom = repository.repoGetTodayPengeluaranRoom

    val headersRoomMutable: MutableLiveData<ArrayList<HeaderSalesRoom>> =
        MutableLiveData<ArrayList<HeaderSalesRoom>>()

    val today = getCurrentDate()

    var sumTotal = MutableLiveData<Int>()


    fun vmSumtotal(totalIncomeCash: String?, totalPengeluaranCash: String?) {
    sumTotal.value = totalIncomeCash!!.toInt() - totalPengeluaranCash!!.toInt()

    }

    init {
        sumTotal.value=0
    }

    private fun getCurrentDate(): String {
//        val date = Calendar.getInstance().time
//        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
//        val formatedDate = formatter.format(date)

        val sdf = SimpleDateFormat("MM/dd/YYYY")
        return sdf.format(Date())
    }


    fun save(headerArrayList: ArrayList<HeaderSales>, idTag: String) {
        vmInsertRepoHeadersRoom(headerArrayList)
        savePaymentIncomeList(headerArrayList, idTag)
    }

    fun savePengeluaran(allPengArrayList: ArrayList<Pengeluaran>, idTag: String) {
        vmRepoInsertPengeluaranRoom(allPengArrayList)
        vmRepoTodayPengeluaranRoom(allPengArrayList, idTag)
    }

    fun saveTakeHomeMoney(inputTotaltransaksi: ArrayList<TakeHomeMoneyRoom>) : Job=
        viewModelScope.launch {
            repository.repoInsertTakeHomeMoneyRoom(inputTotaltransaksi)
        }

    private fun vmRepoTodayPengeluaranRoom(
        allPengArrayList: ArrayList<Pengeluaran>,
        idTag: String,
    ): Job =
        viewModelScope.launch {
            //vmRepoDellAllTodayPengeluaranRoom()
            var todayPengArrayList = allPengArrayList.filter {
                (it.idTag == idTag && it.delete == "1" && it.tanggal == today) //ini memfilter data yang di miliki idtag dan yang memiliki status tidak di didelete
            } as ArrayList<Pengeluaran>
            for (pointer in todayPengArrayList.indices){
                val inserToRoom=TodayPengeluaranRoom(null,todayPengArrayList[pointer].idTag,
                    todayPengArrayList[pointer].idThis,todayPengArrayList[pointer].varianPengeluaran,
                    todayPengArrayList[pointer].jmlPengeluaran,todayPengArrayList[pointer].payment,
                    todayPengArrayList[pointer].tanggal,todayPengArrayList[pointer].jam,
                    todayPengArrayList[pointer].record,todayPengArrayList[pointer].delete)
                repository.repoInsertTodayPengeluaranRoom(inserToRoom)
            }
        }




    private fun vmRepoInsertPengeluaranRoom(allPengArrayList: ArrayList<Pengeluaran>): Job =
        viewModelScope.launch {
            for (pointer in allPengArrayList.indices) {
                val insertToRoom = PengeluaranRoom(null, allPengArrayList[pointer].idTag,
                    allPengArrayList[pointer].idThis, allPengArrayList[pointer].varianPengeluaran,
                    allPengArrayList[pointer].jmlPengeluaran, allPengArrayList[pointer].payment,
                    allPengArrayList[pointer].tanggal, allPengArrayList[pointer].jam,
                    allPengArrayList[pointer].record, allPengArrayList[pointer].delete)
                repository.repoInsertPengeluaranRoom(insertToRoom)
            }


        }


    fun savePaymentIncomeList(headerArrayList: ArrayList<HeaderSales>, idTag: String) {
        var filteredHeaderTranx = headerArrayList.filter {
            //            it.idTag == idTag
            (it.idTag == idTag && it.delete == "1" && it.tanggal == today) //ini memfilter data yang di miliki idtag dan yang memiliki status tidak di didelete
            //  (it.tanggal == today)
        } as ArrayList<HeaderSales>

        println("47 $filteredHeaderTranx")
        println("47 $headerArrayList")
        println("47 $idTag")

        //send to get summary
        getPaymentSummary(filteredHeaderTranx)
    }

    fun getPaymentSummary(filteredHeaderTranx: ArrayList<HeaderSales>) {
        println("56 $filteredHeaderTranx")
        //panggil clear paymentincomelist
       // vmdelAllPaymentIncomeListRoom()

        viewModelScope.launch {
            var tempPaymentType: String? = null
            var pointedPayment: String
            var samePaymentData = ArrayList<HeaderSales>()
            var cekPaymentExist = ArrayList<PaymentIncomeList>()
            var paymentIncomeList = ArrayList<PaymentIncomeList>()
            var tempTotal: Int
            var brpX: Int

            for (pointer in filteredHeaderTranx.indices) {
                pointedPayment = filteredHeaderTranx[pointer].payment.toString()
                tempTotal = 0
                brpX = 0
                if (pointer == 0) {
                    tempPaymentType = pointedPayment
                    samePaymentData.clear()
                    samePaymentData = filteredHeaderTranx.filter {
                        (it.payment == pointedPayment)
                    } as ArrayList<HeaderSales>
                    //
                    for (pointer in samePaymentData.indices) {
                        tempTotal = tempTotal + samePaymentData[pointer].allTotal!!.toInt()
                    }
                    brpX = samePaymentData.size
                    var addTolist = PaymentIncomeList(
                        tempPaymentType,
                        brpX.toString(),
                        tempTotal.toString()
                    )
                    paymentIncomeList.add(addTolist)
                }
                if (pointedPayment == tempPaymentType) {
                    println("143 just Skip")
                    tempPaymentType = pointedPayment
                }
                if (pointer > 0 && pointedPayment != tempPaymentType) {
                    samePaymentData.clear()
                    samePaymentData = filteredHeaderTranx.filter {
                        (it.payment == pointedPayment)
                    } as ArrayList<HeaderSales>
                    println("same $samePaymentData")
                    //cek apakah sudah ada payment di dalam array
                    cekPaymentExist = paymentIncomeList.filter {
                        (it.payment == samePaymentData[0].payment)
                    } as ArrayList<PaymentIncomeList>
                    println("ini 157 $cekPaymentExist")

                    if (cekPaymentExist.isEmpty()) {
                        println("cekpayment $cekPaymentExist")
                        for (pointer in samePaymentData.indices) {
                            tempTotal = tempTotal + samePaymentData[pointer].allTotal!!.toInt()
                        }
                        brpX = samePaymentData.size
                        tempPaymentType = pointedPayment

                        var addTolist = PaymentIncomeList(
                            tempPaymentType,
                            brpX.toString(),
                            tempTotal.toString()
                        )
                        paymentIncomeList.add(addTolist)
                    }
                    if (cekPaymentExist.isNotEmpty()) {
                        println("156 just Skip")
                        tempPaymentType = pointedPayment
                        println("cekpayment $cekPaymentExist")
                    }
                }
            }
//insert to room
            for (pointer in paymentIncomeList.indices) {
                val insertToRoom = PaymentIncomeListRoom(null, paymentIncomeList[pointer].payment,
                    paymentIncomeList[pointer].x, paymentIncomeList[pointer].allTotalPayment)
                repository.insertAllPaymentIncomeListRepRoom(insertToRoom)
            }
//insert to rec??? atau di main activity??
        }
    }

    fun vmInsertRepoHeadersRoom(allInCart: ArrayList<HeaderSales>): Job =
        viewModelScope.launch {
            // var insertToRoom = ArrayList<HeaderSalesRoom>()
            for (pointer in allInCart.indices) {
                //insertToRoom.clear()
                var insertToRoom = HeaderSalesRoom(null, allInCart[pointer].idTag,
                    allInCart[pointer].idTranx, allInCart[pointer].allTotal,
                    allInCart[pointer].record, allInCart[pointer].tanggal,
                    allInCart[pointer].jam, allInCart[pointer].idCustomer,
                    allInCart[pointer].payment, allInCart[pointer].ppn,
                    allInCart[pointer].charge, allInCart[pointer].delete)

                repository.insertRepHeadersRoom(insertToRoom)
                // insertToRoom.

            }

        }
    fun vmRepoUpdateTakeHomeMoneyRoom(newValue:String,id:Int) :Job=
        viewModelScope.launch {
            repository.repoUpdateTakeHomeMoneyRoom(newValue,id)
        }

    fun vmDelOneHeadersRepRoom(allInCart: HeaderSalesRoom): Job =
        viewModelScope.launch {
            repository.delOneHeadersRepRoom(allInCart)
        }

    fun vmdelAllHeadersRepRoom(): Job =
        viewModelScope.launch {
            repository.delAllHeadersRepRoom()
        }

    fun vmRepoDellAllPengeluaranRoom(): Job =
        viewModelScope.launch {
            repository.repoDellAllPengeluaranRoom()
        }
//    fun vmRepoDellAllTodayPengeluaranRoom():Job =
//        viewModelScope.launch {
//            repository.repoDellAllTodayPengeluaranRoom()
//        }

    fun vmdelAllPaymentIncomeListRoom(): Job =
        viewModelScope.launch {
            repository.delAllPaymentIncomeListRepRoom()
        }
    fun vmRepoDellAllTodayPengeluaranRoom() : Job =
        viewModelScope.launch {
            repository.repoDellAllTodayPengeluaranRoom()
        }

    fun vmRepoDelAllTakeHomeMoneyRoom():Job=
        viewModelScope.launch{
        repository.repoDelAllTakeHomeMoneyRoom()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }




}