package com.sameh.foodrecipesapp.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameh.foodrecipesapp.constants.Constants
import com.sameh.foodrecipesapp.model.pojo.Category
import com.sameh.foodrecipesapp.model.repo.RepositoriesImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repo: RepositoriesImp
) : ViewModel() {

    private var _categoriesMutableLiveData = MutableLiveData<List<Category>>()
    val categoriseLiveData: LiveData<List<Category>> get() = _categoriesMutableLiveData

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllCategories()

                if (result.isSuccessful && result.body() != null) {
                    _categoriesMutableLiveData.postValue(result.body()!!.categories)
                } else {
                    Log.d(Constants.TAG, "getAllCategories: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "getAllCategories: ${e.message}")
            }
        }
    }

}