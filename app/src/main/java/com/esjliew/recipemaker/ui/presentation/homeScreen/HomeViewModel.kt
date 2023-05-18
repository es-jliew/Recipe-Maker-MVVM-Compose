package com.esjliew.recipemaker.ui.presentation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esjliew.recipemaker.data.RecipeModel
import com.esjliew.recipemaker.repository.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel (recipeRepository: RecipeRepository) : ViewModel()
{
    private val recipeRepo = recipeRepository
    var recipeModel by mutableStateOf(emptyList<RecipeModel>())

    fun getAll() {
        viewModelScope.launch {
            //recipeRepo.getAll(recipeModel as MutableList<RecipeModel>)
            recipeRepo.getAllRecipe().collect { response ->
                recipeModel = response
            }
        }
    }

    fun addRecipe() {
        viewModelScope.launch {
            recipeRepo.addDemoRecipe()
        }
    }
}