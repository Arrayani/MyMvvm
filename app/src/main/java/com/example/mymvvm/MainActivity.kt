package com.example.mymvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mymvvm.databinding.ActivityMainBinding
import com.example.mymvvm.models.Pengeluaran
import com.example.mymvvm.room.*
import com.example.mymvvm.utils.LapkeuExpenseAdapter
import com.example.mymvvm.utils.LapkeuIncomeAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    var headerArrayList = ArrayList<HeaderSales>()
    var allPengArrayList = ArrayList<Pengeluaran>()
    var takeHomeMoneyArrayList = ArrayList<TakeHomeMoneyRoom>()
    var paymentIncomeList4= ArrayList<PaymentIncomeListRoom>()

    //val idtag = "Rosemedical"
    var idTag: String? = null
    private lateinit var roomViewModel: RoomViewModel

    private lateinit var recyclerviewIncome: RecyclerView
    private lateinit var recyclerviewExpense: RecyclerView

   // private var lapkeuIncomeAdapter:LapkeuIncomeAdapter=LapkeuIncomeAdapter(paymentIncomeList4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        recyclerviewExpense = binding.recViewPengeluaran
        recyclerviewExpense.setHasFixedSize(false)
        recyclerviewIncome = binding.recViewPemasukan
        recyclerviewIncome.setHasFixedSize(false)
        setContentView(binding.root)
        var insert1 = ArrayList<HeaderSales>()
        val today = getCurrentDate()
        binding.tanggalTv.text = today

//        insert1 = ("fdfdj",)
        headerArrayList.add(HeaderSales("Rose Medical","12345","157000","1","$today"
        ,"12:30","1","Cash","0","0","1"))
        headerArrayList.add(HeaderSales("Rose Medical","12346","20000","1","$today"
            ,"12:40","1","Cash","0","0","1"))
        headerArrayList.add(HeaderSales("Rose Medical","12347","300000","3","$today"
            ,"12:50","1","Transfer","0","0","0"))
        println("$headerArrayList")

        allPengArrayList.add(Pengeluaran("Rose Medical","12345","iuran","20000","Uang Penjualan",
        "$today","12.30","1","1"))
        allPengArrayList.add(Pengeluaran("Rose Medical","12346","Makan","15000","Uang Penjualan",
            "$today","12.40","1","1"))
        allPengArrayList.add(Pengeluaran("Rose Medical","12347","Minum","5000","Uang Penjualan",
            "$today","12.45","1","1"))
        allPengArrayList.add(Pengeluaran("Rose Medical","12348","Tagihan","50000","Rekening Bank",
            "$today","12.50","1","1"))


        //ini buat dummy kosong, buat bisa ngeupdate nantinya
//        takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,null))
//        takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,null))
        takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,"0"))
        takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,"0"))
//ini mengakses viewmodel
        val dao:NotaTelDao =AppDatabase.getDataBase(application).notaTelDao()
        val repository =RoomRepository(dao)
        val factory = RoomViewModelFactory(repository)

        idTag = "Rose Medical"

        roomViewModel =  ViewModelProvider(this,factory).get(RoomViewModel::class.java)


        roomViewModel.vmRepoDelAllTakeHomeMoneyRoom()//clearTakeHomeMoney
        roomViewModel.saveTakeHomeMoney(takeHomeMoneyArrayList)


        binding.addBtn.setOnClickListener{
            roomViewModel.save(headerArrayList, idTag!!)
            roomViewModel.savePengeluaran(allPengArrayList,idTag!!)

        }


    binding.displayBtn.setOnClickListener{
   /**------------------------------------------------------------------------------*/
    roomViewModel.vmRepoGetAllHeaderSroom.observe(this) {
        Log.i("MYTAG", it.toString())
        val allIncomeDeal = it.filter {
            (it.idTag == idTag && it.delete == "1" && it.tanggal == today)
        }
        val jmlTransaksi = allIncomeDeal.size
        binding.transaksiTotalTv.text = jmlTransaksi.toString()

        val getPaymentSummary= paymentSummary(it)

        recyclerviewIncome.adapter = LapkeuIncomeAdapter(getPaymentSummary)

        val totalTransaksiCash = it.filter{
            (it.idTag == idTag && it.delete == "1" && it.tanggal == today && it.payment=="Cash")
        }
        if (totalTransaksiCash.isNotEmpty()) {
            var getTotalTransaksi = 0
            for(pointer in totalTransaksiCash.indices){
                getTotalTransaksi += totalTransaksiCash[pointer].allTotal!!.toInt()
            }
            val inputTotaltransaksi = getTotalTransaksi.toString()
           // takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,inputTotaltransaksi))
            //binding.cashIncomeTv.text = getTotalTransaksi.toString()
            //binding.cashIncomeTv.text = inputTotaltransaksi
            //roomViewModel.saveTakeHomeMoney(takeHomeMoneyArrayList)
            val satu = 1
            roomViewModel.vmRepoUpdateTakeHomeMoneyRoom(inputTotaltransaksi,satu)
        }
        else{
            binding.cashIncomeTv.text ="0"
        }
    }
  /**------------------------------------------------------------------------------*/
//        roomViewModel.vmRepoGetAllPaymentIncomeListroom.observe(this) {
//            Log.i("newTAG", it.toString())
//            val totalTransaksiCash = it.filter {
//                (it.payment == "Cash")
//            }
//
//            //val getTotalTransaksi = it[0].allTotalPayment
//            if (totalTransaksiCash.isNotEmpty()) {
//            val getTotalTransaksi = totalTransaksiCash[0].allTotalPayment
//            binding.cashIncomeTv.text = getTotalTransaksi
//        }
//        else{
//            binding.cashIncomeTv.text ="0"
//        }
//        }
        /**------------------------------------------------------------------------------*/
        roomViewModel.vmRepoGetAllPengeluaranRoom.observe(this){
            Log.i("AllPengeluaran", it.toString())
            val getTodayPengeluaran = todayPengeluaran(it)
            recyclerviewExpense.adapter = LapkeuExpenseAdapter(getTodayPengeluaran)
           // val totalPenggunaanCash = penggunaanCash(it)
            val inputTotalPenggunaanCash = penggunaanCash(it).toString()
           // takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,inputTotalPenggunaanCash))
            //roomViewModel.saveTakeHomeMoney(takeHomeMoneyArrayList)
            val dua = 2
            roomViewModel.vmRepoUpdateTakeHomeMoneyRoom(inputTotalPenggunaanCash,dua)
            //binding.totalUangPengeluaranTv.text = totalPenggunaanCash.toString()
            //binding.totalUangPengeluaranTv.text = inputTotalPenggunaanCah

        }

        roomViewModel.vmRepoGetTakeHomeMoney.observe(this){
            Log.i("TakeHomeMoney", it.toString())
            //val totalPemasukanCash = it[1].sumTotal!!.toString()
            val totalPemasukanCash = it.first().sumTotal.toString()
            val totalPengeluaranCash = it.last().sumTotal.toString()
           // val totalPengeluaranCash = it[2].sumTotal!!.toString()
            val takeHomeMoney = totalPemasukanCash.toInt()-totalPengeluaranCash.toInt()
            binding.cashIncomeTv.text=totalPemasukanCash
            binding.totalUangPengeluaranTv.text = totalPengeluaranCash
            binding.takeHomeMoneyTv.text = takeHomeMoney.toString()

            val totPemasukan = it[0].sumTotal //bisa juga pake cara ini ngambil datanya
            val totPengeluaran = it[1].sumTotal // bisa juga pake cara ini ngambil datanya
            Log.i("Tot-tot", totPemasukan.toString())
            Log.i("Tot-tot", totPengeluaran.toString())
        }

        /**------------------------------------------------------------------------------*/
//        var totalIncomeCash = binding.cashIncomeTv.text
//        var totalPengeluaranCash = binding.totalUangPengeluaranTv.text
//        roomViewModel.vmSumtotal(totalIncomeCash as String?, totalPengeluaranCash as String?)
//        roomViewModel.sumTotal.observe(this,{
//            binding.takeHomeMoneyTv.text = it.toString()
//        })
        /**------------------------------------------------------------------------------*/
        roomViewModel.vmRepoGetTodayPengeluaranRoom.observe(this){
            Log.i("TodayPengeluaran", it.toString())

           // recyclerviewExpense = binding.recViewPengeluaran
          //  recyclerviewExpense.adapter = LapkeuExpenseAdapter(it)
            //recyclerviewIncome.setHasFixedSize(false)
        }
    }


       // roomViewModel.vmRepGetAll
      binding.delBtn.setOnClickListener{
          roomViewModel.vmdelAllHeadersRepRoom()
          roomViewModel.vmdelAllPaymentIncomeListRoom()



//          roomViewModel.vmGetAllRepoPaymentIncomeListroom.observe(this) {
//              roomViewModel.vmdelAllHeadersRepRoom()
//              roomViewModel.vmdelAllPaymentIncomeListRoom()
//              Log.i("newTAG", it.toString())
////              recyclerviewIncome = binding.recViewPemasukan
////              recyclerviewIncome.adapter = LapkeuIncomeAdapter(it)
////              recyclerviewIncome.setHasFixedSize(false)
//         }


//          roomViewModel.vmRepoGetTodayPengeluaranRoom.observe(this){
//              roomViewModel.vmRepoDellAllPengeluaranRoom()
//              roomViewModel.vmRepoDellAllTodayPengeluaranRoom()
//              Log.i("TodayPengeluaran", it.toString())
//              recyclerviewExpense = binding.recViewPengeluaran
//              recyclerviewExpense.adapter = LapkeuExpenseAdapter(it)
//              recyclerviewIncome.setHasFixedSize(false)
//          }

          roomViewModel.vmRepoDellAllPengeluaranRoom()
          roomViewModel.vmRepoDellAllTodayPengeluaranRoom()
//          roomViewModel.vmRepoDelAllTakeHomeMoneyRoom()//clearTakeHomeMoney
//          takeHomeMoneyArrayList.clear()
//          takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,null))
//          takeHomeMoneyArrayList.add(TakeHomeMoneyRoom(null,null))
//          roomViewModel.saveTakeHomeMoney(takeHomeMoneyArrayList)
          val satu = 1
          roomViewModel.vmRepoUpdateTakeHomeMoneyRoom("0",satu)
          val dua = 2
          roomViewModel.vmRepoUpdateTakeHomeMoneyRoom("0",dua)
      }
    }

    private fun todayPengeluaran(it: List<PengeluaranRoom>?): List<PengeluaranRoom> {
            var today = getCurrentDate()
            var todayPengArrayList = it!!.filter {
                (it.idTag == idTag && it.delete == "1" && it.tanggal ==today) //ini memfilter data yang di miliki idtag dan yang memiliki status tidak di didelete
            } as ArrayList<PengeluaranRoom>
            return todayPengArrayList
    }

    private fun penggunaanCash(it: List<PengeluaranRoom>?): Int {
        var today = getCurrentDate()
        var returnTotalPengeluaran :Int =0
        var todayPengArrayList = it!!.filter {
            (it.idTag == idTag && it.delete == "1" && it.tanggal ==today && it.payment=="Uang Penjualan") //ini memfilter data yang di miliki idtag dan yang memiliki status tidak di didelete
        } as ArrayList<PengeluaranRoom>

        for (pointer in todayPengArrayList.indices){
            var temp= todayPengArrayList[pointer].jmlPengeluaran!!.toInt()
            returnTotalPengeluaran+=temp
        }
        return returnTotalPengeluaran
    }






    private fun paymentSummary(it: List<HeaderSalesRoom>?): MutableList<PaymentIncomeListRoom> {
        var tempPaymentType: String? = null
        var pointedPayment: String
        var samePaymentData = ArrayList<HeaderSalesRoom>()
        var cekPaymentExist = ArrayList<PaymentIncomeList>()
        var paymentIncomeList : MutableList<PaymentIncomeListRoom> = mutableListOf()
        var tempTotal: Int
        var brpX: Int

        for (pointer in it!!.indices) {
            pointedPayment = it[pointer].payment.toString()
            tempTotal = 0
            brpX = 0
            if (pointer == 0) {
                tempPaymentType = pointedPayment
                samePaymentData.clear()
                samePaymentData = it.filter {
                    (it.payment == pointedPayment)
                } as ArrayList<HeaderSalesRoom>
                //
                for (pointer in samePaymentData.indices) {
                    tempTotal = tempTotal + samePaymentData[pointer].allTotal!!.toInt()
                }
                brpX = samePaymentData.size
                var addTolist = PaymentIncomeListRoom(null,
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
                samePaymentData = it.filter {
                    (it.payment == pointedPayment)
                } as ArrayList<HeaderSalesRoom>
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

                    var addTolist = PaymentIncomeListRoom(null,
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


        return paymentIncomeList
    }

    private fun getCurrentDate(): String {
//        val date = Calendar.getInstance().time
//        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
//        val formatedDate = formatter.format(date)

        val sdf = SimpleDateFormat("MM/dd/YYYY")
        return sdf.format(Date())
    }


}
