package com.esjliew.recipemaker.ui.presentation.viewRecipeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esjliew.recipemaker.data.RecipeModel
import com.esjliew.recipemaker.repository.RecipeRepository
import io.objectbox.annotation.Id
import kotlinx.coroutines.launch

class ViewRecipeViewModel(recipeRepository: RecipeRepository) : ViewModel() {
    private val recipeRepo = recipeRepository
    var recipeModel by mutableStateOf(RecipeModel())

    fun getRecipeById(recipeId: Long) {
        viewModelScope.launch {
            //recipeRepo.getAll(recipeModel as MutableList<RecipeModel>)
            recipeModel = recipeRepo.getRecipeById(recipeId)
        }
    }
}