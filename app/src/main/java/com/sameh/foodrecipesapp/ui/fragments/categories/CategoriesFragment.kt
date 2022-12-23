package com.sameh.foodrecipesapp.ui.fragments.categories

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sameh.foodrecipesapp.databinding.FragmentCategoriesBinding
import com.sameh.foodrecipesapp.model.viewmodel.CategoriesViewModel
import com.sameh.foodrecipesapp.ui.fragments.categories.adapter.CategoriesInfoAdapter
import com.sameh.foodrecipesapp.utils.isOnline
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    @Inject
    lateinit var categoriesInfoAdapter: CategoriesInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getAllCategories()
        categoriesObserver()

        if (!isOnline(requireContext())) {
            binding.progressBar.visibility = View.GONE
            binding.imgNoInternet.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        binding.recView.adapter = categoriesInfoAdapter
        binding.recView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.recView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 200
        }
    }

    private fun getAllCategories() {
        binding.progressBar.visibility = View.VISIBLE
        categoriesViewModel.getAllCategories()
    }

    private fun categoriesObserver() {
        categoriesViewModel.categoriseLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                categoriesInfoAdapter.setData(it)
            }
        }
    }

}