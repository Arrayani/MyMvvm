package com.example.mymvvm.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymvvm.databinding.PemasukanLapkeuCardBinding
import com.example.mymvvm.room.PaymentIncomeListRoom

//class LapkeuIncomeAdapter(var paymentIncomeList: List<PaymentIncomeListRoom>) :
class LapkeuIncomeAdapter(var paymentIncomeList: List<PaymentIncomeListRoom>) :
    RecyclerView.Adapter<LapkeuIncomeAdapter.MyViewHolder>() {

    //        get() {
//            return emptyList()
//        }
//        set(value) {}


    fun setData(freshList: List<PaymentIncomeListRoom>){
        val oldList = this.paymentIncomeList

        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ListDiffCallback(oldList,freshList)
        )
        this.paymentIncomeList=freshList
        diffResult.dispatchUpdatesTo(this)}

    class MyViewHolder(val binding: PemasukanLapkeuCardBinding) :
        RecyclerView.ViewHolder(binding.root) {}




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PemasukanLapkeuCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = paymentIncomeList[position]
        holder.binding.paymentTypeTv.text = currentitem.payment
        var jumlahPemasukan = currentitem.allTotalPayment
        jumlahPemasukan = ValidNumber().deciformat(jumlahPemasukan.toString())
        holder.binding.jmlIncomeTv.text = jumlahPemasukan
        var berapaX = currentitem.x
        berapaX = ValidNumber().deciformat(berapaX.toString())
        holder.binding.berapaXTv.text = berapaX

    }

    override fun getItemCount(): Int {
        return paymentIncomeList.size
    }

    }

    class ListDiffCallback(
        private var oldList: List<PaymentIncomeListRoom>,
        private var newList: List<PaymentIncomeListRoom>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].no == newList[newItemPosition].no)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when {
                oldList[oldItemPosition].no != newList[newItemPosition].no -> {
                    false
                }
                oldList[oldItemPosition].payment != newList[newItemPosition].payment -> {
                    false
                }
                oldList[oldItemPosition].x != newList[newItemPosition].x -> {
                    false
                }
                oldList[oldItemPosition].allTotalPayment != newList[newItemPosition].allTotalPayment -> {
                    false
                }

                else -> {
                    true
                }
            }
        }
    }
