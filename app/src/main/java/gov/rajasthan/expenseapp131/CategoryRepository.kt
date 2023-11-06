package gov.rajasthan.expenseapp131

class CategoryRepository(val db : MyRoomDataBase) {

    fun addCategory(newCategory : CategoryTable){
        db.CategoryDao().addCategory(newCategory)
    }

    fun getAllCategory() : List<CategoryTable>{
        return db.CategoryDao().getAllCategory()
    }


}