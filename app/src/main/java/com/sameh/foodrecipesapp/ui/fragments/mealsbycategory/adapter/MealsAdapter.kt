package com.sameh.foodrecipesapp.ui.fragments.mealsbycategory.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.databinding.ItemOnRecyclerviewInFragmentMealsByCategoriesBinding
import com.sameh.foodrecipesapp.model.pojo.MealByCategory
import javax.inject.Inject

class MealsAdapter @Inject constructor(private val context: Application):
ListAdapter<MealByCategory, MealsAdapter.ViewHolder>(MealDiffCallBack()){

    var onMealClickListener: OnMealClickListener? = null

    fun setData(mealByCategoryList: List<MealByCategory>) {
        this.submitList(mealByCategoryList)
    }

    inner class ViewHolder(private val binding: ItemOnRecyclerviewInFragmentMealsByCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(mealByCategory: MealByCategory) {
                Glide.with(context)
                    .load(mealByCategory.strMealThumb)
                    .into(binding.imgMeal)

                binding.tvMealName.text = mealByCategory.strMeal

                binding.cardViewContainer.setOnClickListener {
                    onMealClickListener?.onMealClickListener(mealByCategory)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOnRecyclerviewInFragmentMealsByCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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