package gov.rajasthan.expenseapp131

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gov.rajasthan.expenseapp131.databinding.MainRowBinding

class RecyclerMainAdpter(val context: Context, val arrData: ArrayList<MainModel>) : RecyclerView.Adapter<RecyclerMainAdpter.ViewHolder>(){
    class ViewHolder(val binding: MainRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MainRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtDate.text = arrData[position].date
        holder.binding.txtAmt.text = arrData[position].amt
        holder.binding.RecySeccondryView.layoutManager = LinearLayoutManager(context)
        holder.binding.RecySeccondryView.adapter = RecyclerSecondaryAdpter(context, arrData[position].listExpense)
    }
}