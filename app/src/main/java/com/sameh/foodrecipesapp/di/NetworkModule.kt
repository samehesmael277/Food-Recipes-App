package com.sameh.foodrecipesapp.di

import com.sameh.foodrecipesapp.constants.Constants
import com.sameh.foodrecipesapp.model.remote.MealApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMealApiServer(): MealApi =
        Retrofit.Builder()
            .baseUrl(Constants.MEAL_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)

}