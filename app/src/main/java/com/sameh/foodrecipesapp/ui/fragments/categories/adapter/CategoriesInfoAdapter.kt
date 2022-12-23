package com.sameh.foodrecipesapp.ui.fragments.categories.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.databinding.ItemOnRecOnCategoriesInfoFrgBinding
import com.sameh.foodrecipesapp.model.pojo.Category
import javax.inject.Inject

class CategoriesInfoAdapter @Inject constructor(private val context: Application):
ListAdapter<Category, CategoriesInfoAdapter.ViewHolder>(CategoriesInfoDiffCallBack()){

    fun setData(categories: List<Category>) {
        this.submitList(categories)
    }

    inner class ViewHolder(private val binding: ItemOnRecOnCategoriesInfoFrgBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            Glide.with(context)
                .load(category.strCategoryThumb)
                .into(binding.imgCategory)

            binding.tvCategoryName.text = category.strCategory
            binding.tvMealDec.text = category.strCategoryDescription
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOnRecOnCategoriesInfoFrgBinding.inflate(LayoutInflater.from(context),
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

class CategoriesInfoDiffCallBack: DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}