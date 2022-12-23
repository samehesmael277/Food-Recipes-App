package com.sameh.foodrecipesapp.ui.fragments.home.adapter.mostpopularmeals

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.databinding.ItemOnPopularRecyclerViewBinding
import com.sameh.foodrecipesapp.model.pojo.MealByCategory
import javax.inject.Inject

class MostPopularMealsAdapter @Inject constructor(private val context: Application) :
    ListAdapter<MealByCategory, MostPopularMealsAdapter.ViewHolder>(MealDiffCallBack()) {

    var onItemViewClickListener: OnPopularMealsClickListener? = null

    fun setData(mealByCategory: List<MealByCategory>) {
        this.submitList(mealByCategory)
    }

    inner class ViewHolder(private val binding: ItemOnPopularRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(mealByCategory: MealByCategory) {
            Glide.with(context)
                .load(mealByCategory.strMealThumb)
                .into(binding.imgPopularMeal)

            binding.cardViewContainerOnPopularMeals.setOnClickListener {
                onItemViewClickListener?.onPopularMealsClickListener(mealByCategory.idMeal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOnPopularRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

}

class MealDiffCallBack : DiffUtil.ItemCallback<MealByCategory>() {

    override fun areItemsTheSame(oldItem: MealByCategory, newItem: MealByCategory): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealByCategory, newItem: MealByCategory): Boolean {
        return oldItem == newItem
    }

}