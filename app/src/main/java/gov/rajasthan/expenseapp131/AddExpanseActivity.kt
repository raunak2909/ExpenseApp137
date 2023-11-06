package gov.rajasthan.expenseapp131

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import gov.rajasthan.expenseapp131.adapter.CategoryRecyclerAdpater
import gov.rajasthan.expenseapp131.databinding.ActivityAddExpanseBinding
import gov.rajasthan.expenseapp131.databinding.CatorgyDialogBinding
import java.util.Calendar

class AddExpanseActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddExpanseBinding
    lateinit var expenseViewModel: ExpenseViewModel
    lateinit var dialogAdd : Dialog
    var balance = 0

    var selectedType = 0
    var selectedDate = ""
    var selectedCatId = ""

    companion object{
        const val BALANCE_KEY = "balance"
        var arrData = ArrayList<CategoryModal>().apply {
            add(CategoryModal(1,R.drawable.dogicon, "Dog"))
            add(CategoryModal(2,R.drawable.airplane, "Travel"))
            add(CategoryModal(3,R.drawable.cookie, "Food"))
            add(CategoryModal(4,R.drawable.shirt, "Cloth"))
            add(CategoryModal(5,R.drawable.ic_coffee, "Coffee"))
            add(CategoryModal(6,R.drawable.ic_grocery, "Grocery"))
            add(CategoryModal(7,R.drawable.ic_gas_station, "Petrol"))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpanseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        balance = intent.getStringExtra(BALANCE_KEY)!!.toInt()

        val db = MyRoomDataBase.getIntance(this)
        val repository = ExpenseRepository(db)

        expenseViewModel = ViewModelProvider(
            this,
            ExpenseViewModelFactory(repository)
        )[ExpenseViewModel::class.java]


        val dropDownList = listOf("Debit", "Credit") //debit -> 0, credit -> 1
        val adpter = ArrayAdapter(this, R.layout.dropdown_list, dropDownList)

        binding.dropdownMenu.setAdapter(adpter)
        binding.dropdownMenu.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                selectedType = index
                Log.d("selectedIndex", selectedType.toString())
            }

        }





        binding.btnAddCatg.setOnClickListener {

            dialogAdd = Dialog(this)
            val dialogBinding = CatorgyDialogBinding.inflate(layoutInflater)
            dialogAdd.setContentView(dialogBinding.root)

            dialogBinding.recyclerCatorgyDialog.layoutManager = GridLayoutManager(this, 4)
            dialogBinding.recyclerCatorgyDialog.adapter = CategoryRecyclerAdpater(this, arrData)

            dialogAdd.show()
        }

        binding.selectDate.setOnClickListener {

        val calendar = Calendar.getInstance()
            val currYear = calendar.get(Calendar.YEAR)
            val currMonth = calendar.get(Calendar.MONTH)
            val currDate = calendar.get(Calendar.DAY_OF_MONTH)
            val currHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currMin = calendar.get(Calendar.MINUTE)



            DatePickerDialog(this,
                { p0, mYear, mMonth, mDate ->

                    selectedDate = "$mDate/${mMonth+1}/$mYear"
                    Log.d("SelectedDate", selectedDate)
                    binding.selectDate.text = selectedDate

                    /*TimePickerDialog(this, object:TimePickerDialog.OnTimeSetListener{
                        override fun onTimeSet(p0: TimePicker?, mHour: Int, mMin: Int) {
                            //selectedDate = "$selectedDate $mHour:$mMin"
                            //Log.d("SelectedDateTime", selectedDate)

                           //binding.selectDate.text = selectedDate

                        }

                    }, currHour, currMin, true).show()*/

                }, currYear, currMonth, currDate).show()


        }

        binding.btnSubmit.setOnClickListener {


                val calendar = Calendar.getInstance()
                /*val currYear = calendar.get(Calendar.YEAR)
                val currMonth = calendar.get(Calendar.MONTH)
                val currDate = calendar.get(Calendar.DAY_OF_MONTH)*/
                val currHour = calendar.get(Calendar.HOUR_OF_DAY)
                val currMin = calendar.get(Calendar.MINUTE)
                selectedDate = "$selectedDate $currHour : $currMin"


            if(selectedType==0){ //debit
                balance -= binding.edtAmount.text.toString().toInt()
            } else {//credit
                balance += binding.edtAmount.text.toString().toInt()
            }


            expenseViewModel.addExpense(
                ExpenseTable(
                    id = 0,
                    title = binding.edtTitle.text.toString(),
                    desc = binding.edtDesc.text.toString(),
                    amount = binding.edtAmount.text.toString(),
                    balance = balance.toString(),
                    date = selectedDate,
                    catId = selectedCatId,
                    type = selectedType.toString()
                )
            )

            finish()
        }


    }

    fun onCategoryDialogSelected(index: Int){
        dialogAdd.dismiss()
        val selectedCat = arrData[index]

        binding.ctgImg.setImageResource(selectedCat.imgPath)
        binding.ctgText.text = selectedCat.catgName
        selectedCatId = selectedCat.catId.toString()
    }
}