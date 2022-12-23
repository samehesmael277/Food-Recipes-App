package com.sameh.foodrecipesapp.model.repo

import com.sameh.foodrecipesapp.model.pojo.CategoryList
import com.sameh.foodrecipesapp.model.pojo.MealByCategoryList
import com.sameh.foodrecipesapp.model.pojo.MealsList
import retrofit2.Response

interface RemoteRepo {

    suspend fun getRandomMeal(): Response<MealsList>

    suspend fun getMealDetailsById(id: String): Response<MealsList>

    suspend fun getPopularMeals(categoryName: String): Response<MealByCategoryList>

    suspend fun getAllCategories(): Response<CategoryList>

    suspend fun getMealsByCategory(nameOfCategory: String): Response<MealByCategoryList>

}