package gov.rajasthan.expenseapp131

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class ExpenseTable(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val desc: String,
    val amount: String,
    val balance: String,
    val date: String,
    val catId: String,
    val type: String,
)
