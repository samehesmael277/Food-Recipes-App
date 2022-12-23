package com.sameh.foodrecipesapp.ui.fragments.meal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.R
import com.sameh.foodrecipesapp.databinding.FragmentMealBinding
import com.sameh.foodrecipesapp.model.pojo.Meal
import com.sameh.foodrecipesapp.model.viewmodel.LocalDatabaseViewModel
import com.sameh.foodrecipesapp.model.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealFragment : Fragment() {

    private lateinit var binding: FragmentMealBinding

    private val mealViewModel: MealViewModel by viewModels()
    private val localDatabaseViewModel: LocalDatabaseViewModel by viewModels()

    private val args by navArgs<MealFragmentArgs>()
    private lateinit var mealLinkOnYoutube: String
    private lateinit var currentMeal: Meal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoadingData()
        mealViewModel.getMealDetailsById(args.id)

        mealViewModel.mealDetailsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                currentMeal = it
                mealLinkOnYoutube = it.strYoutube.toString()
                showDataInUI(it)
            }
        }

        binding.imgGoToWatchVideo.setOnClickListener {
            goToWatchVideo(mealLinkOnYoutube)
        }

        binding.faBtnAddToFavorite.setOnClickListener {
            insertAndUpdateMealToDb()
        }

    }

    private fun showDataInUI(meal: Meal) {
        try {
            Glide.with(this@MealFragment)
                .load(meal.strMealThumb)
                .into(binding.imgMeal)

            val category = "${getString(R.string.category)} ${meal.strCategory} "
            val area = "${getString(R.string.area)} ${meal.strArea} "
            binding.collapsingToolbarLayout.title = meal.strMeal
            binding.tvCategory.text = category
            binding.tvArea.text = area
            binding.tvInstructionsDescription.text = meal.strInstructions

            afterLoadingData()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToWatchVideo(link: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoadingData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.imgMeal.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvInstructionsDescription.visibility = View.INVISIBLE
    }

    private fun afterLoadingData() {
        binding.progressBar.visibility = View.GONE
        binding.imgMeal.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvInstructionsDescription.visibility = View.VISIBLE
    }

    private fun insertAndUpdateMealToDb() {
        localDatabaseViewModel.insertAndUpdateMealIntoDB(currentMeal)
        Toast.makeText(requireContext(), "Added to favorite successfully", Toast.LENGTH_SHORT).show()
    }

}