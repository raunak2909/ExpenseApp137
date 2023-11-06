package gov.rajasthan.expenseapp131

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import gov.rajasthan.expenseapp131.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var expenseViewModel: ExpenseViewModel
    lateinit var binding: ActivityMainBinding
    var balance = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var db = MyRoomDataBase.getIntance(this)
        var repository = ExpenseRepository(db)
        expenseViewModel = ViewModelProvider(
            this,
            ExpenseViewModelFactory(repository)
        )[ExpenseViewModel::class.java]

        expenseViewModel.getAllExpenses().observe(this) { data ->
            /*for(eachExpense in data){
                Log.d("Expense Data", "expense name: ${eachExpense.title}")
            }*/

            if (data.isNotEmpty()) {
                binding.txtBalance.visibility = View.VISIBLE
                binding.recyclerMainView.visibility = View.VISIBLE
                binding.txtNoExpenses.visibility = View.GONE

                balance = data.last().balance
                binding.txtBalance.text = "\u20B9 $balance"

                val dateWiseData = filterExpenseDateWise(data as ArrayList<ExpenseTable>)

                binding.recyclerMainView.layoutManager = LinearLayoutManager(this)
                binding.recyclerMainView.adapter = RecyclerMainAdpter(this, dateWiseData)
            } else {
                binding.txtBalance.visibility = View.GONE
                binding.recyclerMainView.visibility = View.GONE
                binding.txtNoExpenses.visibility = View.VISIBLE
            }


        }


        /*val  data = ArrayList<MainModel>().apply{
            val secondrydata = ArrayList<SecondaryModal>().apply {
                add(SecondaryModal(R.drawable.dogicon, "Pets", "$3.35", "Treat", "$753"))
                add(SecondaryModal(R.drawable.cookie, "Snacks", "$2.35", "office", "$7383"))
                add(SecondaryModal(R.drawable.airplane, "Travel", "$8.35", "Treat", "$3874853"))
                add(SecondaryModal(R.drawable.shirt, "Salary", "$33.35", "office", "$74848585"))
            }
            add(MainModel("Today", "$100",secondrydata ))
            val secondrydata2 = ArrayList<SecondaryModal>().apply {
                add(SecondaryModal(R.drawable.airplane, "traveler", "$3.35", "Treat", "$753"))
                add(SecondaryModal(R.drawable.cookie, "Snacks", "$2.35", "office", "$7383"))
                add(SecondaryModal(R.drawable.airplane, "Travel", "$8.35", "Treat", "$3874853"))

            }
            add(MainModel("Yesterday", "$75",secondrydata2))

            val secondrydata3 = ArrayList<SecondaryModal>().apply {
                add(SecondaryModal(R.drawable.shirt, "salary", "$3.35", "Treat", "$753"))
                add(SecondaryModal(R.drawable.cookie, "Snacks", "$2.35", "office", "$7383"))

            }
            add(MainModel("23 aug 2023", "100",secondrydata3))
        }
        binding.recyclerMainView.layoutManager = LinearLayoutManager(this)
        binding.recyclerMainView.adapter = RecyclerMainAdpter(this, data)
*/
        binding.floatingActionButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddExpanseActivity::class.java
                ).putExtra(AddExpanseActivity.BALANCE_KEY, balance)
            )
        }

    }

    fun filterExpenseDateWise(expenseData: ArrayList<ExpenseTable>): ArrayList<MainModel> {

        val arrMainModel = ArrayList<MainModel>()

        val arrUniqueDates = ArrayList<String>()

        for (eachExpense in expenseData) {

            val eachExpenseDate = eachExpense.date.split(" ")[0]
            if (!arrUniqueDates.contains(eachExpenseDate)) {
                arrUniqueDates.add(eachExpenseDate)
            }
        }

        Log.d("Unique Dates", arrUniqueDates.toString())

        for (eachDate in arrUniqueDates) {

            val arrEachDateExpenses = ArrayList<ExpenseTable>()
            var eachDateAmt = 0

            for (eachExpense in expenseData) {
                val eachExpenseDate = eachExpense.date.split(" ")[0]

                if (eachDate == eachExpenseDate) {
                    arrEachDateExpenses.add(eachExpense)
                    if (eachExpense.type == "0") {
                        eachDateAmt += eachExpense.amount.toInt()
                    } else {
                        eachDateAmt -= eachExpense.amount.toInt()
                    }
                }
            }
            Log.d("Data: ", "${eachDate}, $eachDateAmt")

            //for Today
            val calendar = Calendar.getInstance()
            val todayYear = calendar.get(Calendar.YEAR)
            val todayMonth = calendar.get(Calendar.MONTH)
            val todayDay = calendar.get(Calendar.DAY_OF_MONTH)
            val todayDate = "$todayDay/$todayMonth/$todayYear"


            //for Yesterday
            calendar.add(Calendar.DAY_OF_MONTH, -1)

            val yesterdayYear = calendar.get(Calendar.YEAR)
            val yesterdayMonth = calendar.get(Calendar.MONTH)
            val yesterdayDay = calendar.get(Calendar.DAY_OF_MONTH)
            val yesterdayDate = "$yesterdayDay/$yesterdayMonth/$yesterdayYear"


            var date  = eachDate

            if(date == todayDate){
                 date = "Today"
            }

            if(date == yesterdayDate){
                date = "Yesterday"
            }



            arrMainModel.add(MainModel(date, eachDateAmt.toString(), arrEachDateExpenses))

        }


        Log.d("ArrMainModel", arrMainModel.toString())

        return arrMainModel

    }
}