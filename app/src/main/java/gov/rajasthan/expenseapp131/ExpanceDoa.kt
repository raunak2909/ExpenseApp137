package gov.rajasthan.expenseapp131

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gov.rajasthan.expenseapp131.ExpenseTable

@Dao
interface ExpenseDoa {


    @Insert
    fun addExpense(newExpense : ExpenseTable)

    @Query("select * from expense")
    fun getAllExpense(): LiveData<List<ExpenseTable>>

    @Update
    fun updateExpense(updateExpense : ExpenseTable)

    @Delete
    fun deleteExpense(deleteExpense : ExpenseTable)


}