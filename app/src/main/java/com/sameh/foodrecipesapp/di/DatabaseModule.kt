package com.sameh.foodrecipesapp.di

import android.content.Context
import androidx.room.Room
import com.sameh.foodrecipesapp.constants.Constants
import com.sameh.foodrecipesapp.model.localdb.MealDao
import com.sameh.foodrecipesapp.model.localdb.MealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMealDatabase(
        @ApplicationContext context: Context
    ): MealDatabase =
        Room.databaseBuilder(context, MealDatabase::class.java, Constants.MEAL_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideMealDao(db: MealDatabase): MealDao = db.mealDao()

}