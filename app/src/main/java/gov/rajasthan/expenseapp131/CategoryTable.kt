package gov.rajasthan.expenseapp131

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryImg: String,
    val categoryName : String
)
