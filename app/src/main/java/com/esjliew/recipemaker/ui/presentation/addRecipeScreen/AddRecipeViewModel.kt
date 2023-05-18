package com.esjliew.recipemaker.ui.presentation.addRecipeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esjliew.recipemaker.data.RecipeModel
import com.esjliew.recipemaker.repository.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val recipeRepository: RecipeRepository):ViewModel() {
    fun addRecipe(recipeModel: RecipeModel) {
        val recipeRepo = recipeRepository

        viewModelScope.launch {
            recipeRepo.addRecipe(recipeModel)
        }
    }
}