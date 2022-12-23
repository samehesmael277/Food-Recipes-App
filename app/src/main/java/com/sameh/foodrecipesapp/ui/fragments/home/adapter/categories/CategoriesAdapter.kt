package com.sameh.foodrecipesapp.ui.fragments.home.adapter.categories

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sameh.foodrecipesapp.databinding.CategoryInRecyclerviewCategoriesBinding
import com.sameh.foodrecipesapp.model.pojo.Category
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(private val context: Application) :
    ListAdapter<Category, CategoriesAdapter.ViewHolder>(MealDiffCallBack()) {

    var onItemClickListener: OnCategoryClickListener? = null

    fun setData (categories: List<Category>) {
        this.submitList(categories)
    }

    inner class ViewHolder(private val binding: CategoryInRecyclerviewCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(category: Category) {
                Glide.with(context)
                    .load(category.strCategoryThumb)
                    .into(binding.imgCategory)

                binding.tvCategoryName.text = category.strCategory

                binding.cardViewContainer.setOnClickListener {
                    onItemClickListener?.onCategoryClickListener(category.strCategory)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryInRecyclerviewCategoriesBinding.inflate(
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

class MealDiffCallBack : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}