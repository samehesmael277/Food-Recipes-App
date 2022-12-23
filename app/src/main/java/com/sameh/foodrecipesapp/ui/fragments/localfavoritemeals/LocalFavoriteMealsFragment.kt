package com.sameh.foodrecipesapp.ui.fragments.localfavoritemeals

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.R
import com.sameh.foodrecipesapp.databinding.FragmentLocalFavoriteMealsBinding
import com.sameh.foodrecipesapp.model.viewmodel.LocalDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalFavoriteMealsFragment : Fragment() {

    private lateinit var binding: FragmentLocalFavoriteMealsBinding

    private val args by navArgs<LocalFavoriteMealsFragmentArgs>()

    private val localDatabaseViewModel: LocalDatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocalFavoriteMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataInUi()

        binding.imgGoToWatchVideo.setOnClickListener {
            goToWatchVideo(args.meal.strYoutube.toString())
        }

        binding.faBtnDeleteMeal.setOnClickListener {
            confirmRemoveData()
        }
    }

    private fun setDataInUi() {
        Glide.with(this@LocalFavoriteMealsFragment)
            .load(args.meal.strMealThumb)
            .into(binding.imgMeal)

        val category = "${getString(R.string.category)} ${args.meal.strCategory} "
        val area = "${getString(R.string.area)} ${args.meal.strArea} "
        binding.collapsingToolbarLayout.title = args.meal.strMeal
        binding.tvCategory.text = category
        binding.tvArea.text = area
        binding.tvInstructionsDescription.text = args.meal.strInstructions
    }

    private fun confirmRemoveData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Remove (${args.meal.strMeal})!")
        builder.setMessage("Are you sure to remove (${args.meal.strMeal})?")

        builder.setPositiveButton("Yes") { _, _ ->
            localDatabaseViewModel.deleteMealFromDB(args.meal)
            Toast.makeText(requireContext(), "(${args.meal.strMeal}) removed!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_localFavoriteMealsFragment_to_favoriteFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.show()
    }

    private fun goToWatchVideo(link: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

}