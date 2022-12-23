package com.sameh.foodrecipesapp.ui.fragments.mealsbycategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.sameh.foodrecipesapp.databinding.FragmentMealsByCategoriesBinding
import com.sameh.foodrecipesapp.model.pojo.MealByCategory
import com.sameh.foodrecipesapp.model.viewmodel.MealsByCategoriesViewModel
import com.sameh.foodrecipesapp.ui.fragments.mealsbycategory.adapter.MealsAdapter
import com.sameh.foodrecipesapp.ui.fragments.mealsbycategory.adapter.OnMealClickListener
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator
import javax.inject.Inject

@AndroidEntryPoint
class MealsByCategoriesFragment : Fragment(), OnMealClickListener {

    private lateinit var binding: FragmentMealsByCategoriesBinding

    private val args by navArgs<MealsByCategoriesFragmentArgs>()

    private val mealsByCategoriesViewModel: MealsByCategoriesViewModel by viewModels()

    @Inject
    lateinit var mealsAdapter: MealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealsByCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        mealsByCategoriesViewModel.getMealsByCategories(args.nameOfCategory)
        mealsByCategoriesObserver()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mealsAdapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        binding.recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 200
        }

        mealsAdapter.onMealClickListener = this
    }

    private fun mealsByCategoriesObserver() {
        mealsByCategoriesViewModel.mealsByCategoriesLiveData.observe(viewLifecycleOwner) {
            binding.tvContOfMeals.text = it.size.toString()
            if (it != null) {
                mealsAdapter.setData(it)
            }
        }
    }

    override fun onMealClickListener(mealByCategory: MealByCategory) {
        goToMealFragment(mealByCategory.idMeal)
    }

    private fun goToMealFragment(mealId: String) {
        val action =
            MealsByCategoriesFragmentDirections.actionMealsByCategoriesFragmentToMealFragment(mealId)
        findNavController().navigate(action)
    }

}