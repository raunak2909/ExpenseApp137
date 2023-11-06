package gov.rajasthan.expenseapp131

import androidx.lifecycle.LiveData

class ExpenseRepository(val db: MyRoomDataBase) {

    fun addExpense(newExpense : ExpenseTable){
        db.ExpenseDoa().addExpense(newExpense)
    }

    fun getAllExpense() : LiveData<List<ExpenseTable>>{
        return db.ExpenseDoa().getAllExpense()
    }


    fun updateExpense(updateExpense : ExpenseTable){

    }

    fun deleteExpense(deleteExpense : ExpenseTable){
        db.ExpenseDoa().deleteExpense(deleteExpense)
    }
}