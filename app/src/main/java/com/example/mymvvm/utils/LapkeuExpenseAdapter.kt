package com.example.mymvvm.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymvvm.databinding.PengeluaranLapkeuCardBinding
import com.example.mymvvm.room.PengeluaranRoom


//class LapkeuExpenseAdapter (var expenseList : ArrayList<PengeluaranRoom>)
//    : RecyclerView.Adapter<LapkeuExpenseAdapter.MyViewHolder>(){
//class LapkeuExpenseAdapter(var expenseList: List<TodayPengeluaranRoom>)
class LapkeuExpenseAdapter(var expenseList: List<PengeluaranRoom>)
    : RecyclerView.Adapter<LapkeuExpenseAdapter.MyViewHolder>(){

    class MyViewHolder(val binding : PengeluaranLapkeuCardBinding) :
        RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PengeluaranLapkeuCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = expenseList[position]
        holder.binding.jenisPengeluaranTv.text = currentitem.varianPengeluaran
        var jumlahPengeluaran =  currentitem.jmlPengeluaran
        jumlahPengeluaran = ValidNumber().deciformat(jumlahPengeluaran.toString())
        holder.binding.jumlahPengeluaranTv.text = jumlahPengeluaran
        holder.binding.jenisPembayaranTv.text = currentitem.payment
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

}