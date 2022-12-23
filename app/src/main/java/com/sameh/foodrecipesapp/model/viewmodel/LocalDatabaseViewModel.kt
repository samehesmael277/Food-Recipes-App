package com.sameh.foodrecipesapp.model.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sameh.foodrecipesapp.constants.Constants
import com.sameh.foodrecipesapp.model.pojo.Meal
import com.sameh.foodrecipesapp.model.repo.RepositoriesImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalDatabaseViewModel @Inject constructor(
    private val repo: RepositoriesImp
) : ViewModel() {

    private var _mealsMutableLiveData = MutableLiveData<List<Meal>>()
    val mealLiveData: LiveData<List<Meal>> get() = _mealsMutableLiveData

    fun insertAndUpdateMealIntoDB(meal: Meal) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.insertAndUpdateMealIntoDB(meal)
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, "insertAndUpdateMealIntoDB: ${e.message}")
        }
    }

    fun deleteMealFromDB(meal: Meal) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.deleteMealFromDB(meal)
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, "deleteMealFromDB: ${e.message}")
        }
    }

    fun getAllMealsFromDB() =
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _mealsMutableLiveData.postValue(repo.getAllMealsFromDB())
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, "getAllMealsFromDB: ${e.message}")
        }

}