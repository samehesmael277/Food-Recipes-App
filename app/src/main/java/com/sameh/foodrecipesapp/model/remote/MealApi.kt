package com.sameh.foodrecipesapp.model.remote

import com.sameh.foodrecipesapp.model.pojo.CategoryList
import com.sameh.foodrecipesapp.model.pojo.MealByCategoryList
import com.sameh.foodrecipesapp.model.pojo.MealsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealsList>

    @GET("lookup.php")
    suspend fun getMealDetailsById(@Query("i") id: String): Response<MealsList>

    @GET("filter.php")
    suspend fun getPopularMeals(@Query("c") categoryName: String): Response<MealByCategoryList>

    @GET("categories.php")
    suspend fun getAllCategories(): Response<CategoryList>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") nameOfCategory: String): Response<MealByCategoryList>

}