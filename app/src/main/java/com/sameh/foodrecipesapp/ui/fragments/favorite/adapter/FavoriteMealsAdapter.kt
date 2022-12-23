package com.sameh.foodrecipesapp.ui.fragments.favorite.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.databinding.ItemOnRecOnFavoriteFrgBinding
import com.sameh.foodrecipesapp.model.pojo.Meal
import javax.inject.Inject

class FavoriteMealsAdapter @Inject constructor(private val context: Application) :
    ListAdapter<Meal, FavoriteMealsAdapter.ViewHolder>(FavoriteMealsDiffCallBack()) {

    var onMealClickListener: OnFavoriteMealClickListener? = null

    fun setData(meals: List<Meal>) {
        this.submitList(meals)
    }

    inner class ViewHolder(private val binding: ItemOnRecOnFavoriteFrgBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(meal: Meal) {
                Glide.with(context)
                    .load(meal.strMealThumb)
                    .into(binding.imgMeal)

                binding.tvMealName.text = meal.strMeal

                binding.cardViewContainer.setOnClickListener {
                    onMealClickListener?.onFavoriteMealClickListener(meal)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOnRecOnFavoriteFrgBinding.inflate(LayoutInflater.from(context),
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


class FavoriteMealsDiffCallBack : DiffUtil.ItemCallback<Meal>() {

    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }

}