package com.sameh.foodrecipesapp.model.repo

import com.sameh.foodrecipesapp.model.pojo.Meal

interface LocalRepo {

    suspend fun insertAndUpdateMealIntoDB(meal: Meal)

    suspend fun deleteMealFromDB(meal: Meal)

    suspend fun getAllMealsFromDB(): List<Meal>

}