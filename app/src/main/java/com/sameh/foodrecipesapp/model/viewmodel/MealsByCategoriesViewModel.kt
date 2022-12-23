package com.sameh.foodrecipesapp.model.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sameh.foodrecipesapp.constants.Constants
import com.sameh.foodrecipesapp.model.pojo.MealByCategory
import com.sameh.foodrecipesapp.model.repo.RepositoriesImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsByCategoriesViewModel @Inject constructor(
    private val repo: RepositoriesImp
) : ViewModel() {

    private var _mealsByCategoriesMutableLiveData = MutableLiveData<List<MealByCategory>>()
    val mealsByCategoriesLiveData: LiveData<List<MealByCategory>> get() = _mealsByCategoriesMutableLiveData

    fun getMealsByCategories(CategoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getMealsByCategory(CategoryName)

                if (result.isSuccessful && result.body() != null) {
                    _mealsByCategoriesMutableLiveData.postValue(result.body()!!.meals)
                } else {
                    Log.d(Constants.TAG, "getMealsByCategories: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "getMealsByCategories: ${e.message}")
            }
        }
    }
}