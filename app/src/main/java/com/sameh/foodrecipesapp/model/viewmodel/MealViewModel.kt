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
class MealViewModel @Inject constructor(
    private val repo: RepositoriesImp
) : ViewModel() {

    private var _mealDetailsMutableLiveData = MutableLiveData<Meal>()
    val mealDetailsLiveData: LiveData<Meal> get() = _mealDetailsMutableLiveData

    fun getMealDetailsById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getMealDetailsById(id)

                if (result.isSuccessful && result.body() != null) {
                    _mealDetailsMutableLiveData.postValue(result.body()!!.meals[0])
                } else {
                    Log.d(Constants.TAG, "getMealDetailsById: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "getMealDetailsById: ${e.message}")
            }
        }
    }

}