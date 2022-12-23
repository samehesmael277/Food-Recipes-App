package com.sameh.foodrecipesapp.ui.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sameh.foodrecipesapp.databinding.FragmentFavoriteBinding
import com.sameh.foodrecipesapp.model.pojo.Meal
import com.sameh.foodrecipesapp.model.viewmodel.LocalDatabaseViewModel
import com.sameh.foodrecipesapp.ui.fragments.favorite.adapter.FavoriteMealsAdapter
import com.sameh.foodrecipesapp.ui.fragments.favorite.adapter.OnFavoriteMealClickListener
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), OnFavoriteMealClickListener {

    private lateinit var binding: FragmentFavoriteBinding

    private val localDatabaseViewModel: LocalDatabaseViewModel by viewModels()

    @Inject
    lateinit var favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFavoriteRecyclerView()
        getDataFromDatabase()
        favoriteMealsObserver()
    }

    private fun setupFavoriteRecyclerView() {
        binding.recView.adapter = favoriteMealsAdapter
        binding.recView.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        binding.recView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 200
        }

        favoriteMealsAdapter.onMealClickListener = this
    }

    private fun getDataFromDatabase() {
        localDatabaseViewModel.getAllMealsFromDB()
    }

    private fun favoriteMealsObserver() {
        localDatabaseViewModel.mealLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                favoriteMealsAdapter.setData(it)
            }
        }
    }

    override fun onFavoriteMealClickListener(meal: Meal) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToLocalFavoriteMealsFragment(meal)
        findNavController().navigate(action)
    }

}