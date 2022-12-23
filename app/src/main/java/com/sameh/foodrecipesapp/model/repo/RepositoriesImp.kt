package com.sameh.foodrecipesapp.model.repo

import com.sameh.foodrecipesapp.model.localdb.MealDao
import com.sameh.foodrecipesapp.model.pojo.Meal
import com.sameh.foodrecipesapp.model.remote.MealApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoriesImp @Inject constructor(
    private val mealDao: MealDao,
    private val api: MealApi
) : RemoteRepo, LocalRepo {

    // Remote
    override suspend fun getRandomMeal() =
        withContext(Dispatchers.IO) {
        api.getRandomMeal()
    }

    override suspend fun getMealDetailsById(id: String) =
        withContext(Dispatchers.IO) {
        api.getMealDetailsById(id)
    }

    override suspend fun getPopularMeals(categoryName: String) =
        withContext(Dispatchers.IO) {
        api.getPopularMeals(categoryName)
    }

    override suspend fun getAllCategories() =
        withContext(Dispatchers.IO) {
        api.getAllCategories()
    }

    override suspend fun getMealsByCategory(nameOfCategory: String) =
        withContext(Dispatchers.IO) {
        api.getMealsByCategory(nameOfCategory)
    }


    // Local
    override suspend fun insertAndUpdateMealIntoDB(meal: Meal) {
        withContext(Dispatchers.IO) {
            mealDao.insertAndUpdateMealIntoDB(meal)
        }
    }

    override suspend fun deleteMealFromDB(meal: Meal) {
        withContext(Dispatchers.IO) {
            mealDao.deleteMealFromDB(meal)
        }
    }

    override suspend fun getAllMealsFromDB() =
        withContext(Dispatchers.IO) {
            mealDao.getAllMealsFromDB()
        }

}

