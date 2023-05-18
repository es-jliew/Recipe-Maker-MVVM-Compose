package com.esjliew.recipemaker.ui

sealed class RecipeAppScreens(val name : String){
    object Home : RecipeAppScreens("home")
    object AddRecipe : RecipeAppScreens("add_recipe")
    object AddIngredient: RecipeAppScreens("add_ingredient")
    object UpdateRecipe : RecipeAppScreens("update_recipe/{id}")
    object ViewRecipe : RecipeAppScreens ("view_recipe/{id}")
}