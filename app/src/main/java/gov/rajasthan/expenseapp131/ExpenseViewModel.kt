package gov.rajasthan.expenseapp131

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ExpenseViewModel(val repository: ExpenseRepository) : ViewModel() {

    fun addExpense(newExpense: ExpenseTable){
        repository.addExpense(newExpense)
    }

    fun getAllExpenses() : LiveData<List<ExpenseTable>>{
        return repository.getAllExpense()
    }


}