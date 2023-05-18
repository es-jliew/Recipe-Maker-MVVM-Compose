package com.esjliew.recipemaker.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.esjliew.recipemaker.ui.presentation.addIngredientScreen.AddIngredientScreen
import com.esjliew.recipemaker.ui.presentation.addRecipeScreen.AddRecipeScreen
import com.esjliew.recipemaker.ui.presentation.homeScreen.HomeScreen
import com.esjliew.recipemaker.ui.presentation.updateRecipeScreen.UpdateRecipeScreen
import com.esjliew.recipemaker.ui.presentation.viewRecipeScreen.ViewRecipeScreen

@Preview
@Composable
fun RecipeApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = RecipeAppScreens.Home.name,
    ) {
        composable(route = RecipeAppScreens.Home.name) {
            HomeScreen(
                onFabClicked = { navController.navigate(RecipeAppScreens.AddRecipe.name) },
                navigateToViewRecipeScreen = { recipeId ->
                    navController.navigate("${RecipeAppScreens.ViewRecipe.name}/$recipeId")
                },
            )
        }
        composable(route = "${RecipeAppScreens.UpdateRecipe.name}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            UpdateRecipeScreen(
                recipeId = recipeId,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(RecipeAppScreens.AddRecipe.name) {
            AddRecipeScreen(
                onNextClicked = { navController.navigate(RecipeAppScreens.AddIngredient.name) },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(RecipeAppScreens.AddIngredient.name) {
            AddIngredientScreen(
                onNextClicked = { navController.navigate(RecipeAppScreens.AddIngredient.name) },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = "${RecipeAppScreens.ViewRecipe.name}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            ViewRecipeScreen(
                recipeId = recipeId,
                onFabClicked = { navController.navigate(RecipeAppScreens.AddRecipe.name) },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}