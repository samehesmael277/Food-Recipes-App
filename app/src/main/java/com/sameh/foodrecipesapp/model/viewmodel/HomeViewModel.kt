package com.sameh.foodrecipesapp.model.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sameh.foodrecipesapp.constants.Constants
import com.sameh.foodrecipesapp.model.pojo.Category
import com.sameh.foodrecipesapp.model.pojo.MealByCategory
import com.sameh.foodrecipesapp.model.pojo.Meal
import com.sameh.foodrecipesapp.model.repo.RepositoriesImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: RepositoriesImp
) : ViewModel(){

    private var _mealRandomMutableLiveData = MutableLiveData<Meal>()
    val mealRandomLiveData: LiveData<Meal> get() = _mealRandomMutableLiveData

    private var _popularMealMutableLiveData = MutableLiveData<List<MealByCategory>>()
    val popularMealLiveData: LiveData<List<MealByCategory>> get() = _popularMealMutableLiveData

    private var _allCategoriesMutableLiveData = MutableLiveData<List<Category>>()
    val allCategoriesLiveData: LiveData<List<Category>> get() = _allCategoriesMutableLiveData

    fun getRandomMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getRandomMeal()

                if (result.isSuccessful && result.body() != null) {
                    _mealRandomMutableLiveData.postValue(result.body()!!.meals[0])
                }
                else {
                    Log.d(Constants.TAG, "getRandomMeal: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "getRandomMealException: ${e.message}")
            }
        }
    }

    fun getPopularMealByCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getPopularMeals(categoryName)

                if (result.isSuccessful && result.body() != null) {
                    _popularMealMutableLiveData.postValue(result.body()!!.meals)
                } else {
                    Log.d(Constants.TAG, "getPopularMealByCategory: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "getPopularMealByCategory: ${e.message}")
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllCategories()

                if (result.isSuccessful && result.body() != null) {
                    _allCategoriesMutableLiveData.postValue(result.body()!!.categories)
                } else {
                    Log.d(Constants.TAG, "getAllCategories: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "getAllCategories: ${e.message}")
            }
        }
    }

}