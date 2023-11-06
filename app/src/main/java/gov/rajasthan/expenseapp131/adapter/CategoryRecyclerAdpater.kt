package gov.rajasthan.expenseapp131.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gov.rajasthan.expenseapp131.AddExpanseActivity
import gov.rajasthan.expenseapp131.CategoryModal
import gov.rajasthan.expenseapp131.databinding.CategoryIteamRawBinding

class CategoryRecyclerAdpater(val context : Context, val arrdata : ArrayList<CategoryModal>) : RecyclerView.Adapter<CategoryRecyclerAdpater.ViewHolder>(){
    class ViewHolder(val binding: CategoryIteamRawBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryIteamRawBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrdata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currModel = arrdata[position]

        holder.binding.imgCatg.setImageResource(currModel.imgPath)
        holder.binding.textcatgName.text = currModel.catgName

        holder.binding.llRow.setOnClickListener {
            (context as AddExpanseActivity).onCategoryDialogSelected(position)
        }

    }

}