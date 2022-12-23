package com.sameh.foodrecipesapp.model.localdb

import androidx.room.*
import com.sameh.foodrecipesapp.model.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndUpdateMealIntoDB(meal: Meal)

    @Delete
    suspend fun deleteMealFromDB(meal: Meal)

    @Query("SELECT * FROM meal_table")
    suspend fun getAllMealsFromDB(): List<Meal>

}