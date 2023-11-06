package gov.rajasthan.expenseapp131

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategaryDao {

    @Insert
    fun addCategory(newCategory : CategoryTable)

    @Query("select * from category")
    fun getAllCategory(): List<CategoryTable>

    @Update
    fun updateCategory(newCategory : CategoryTable)

    @Delete
    fun deleteCategory(newCategory : CategoryTable)
}