package com.esjliew.recipemaker.ui.presentation.addRecipeScreen

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.esjliew.recipemaker.R
import com.esjliew.recipemaker.data.RecipeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeTopBar(
    viewModel: AddRecipeViewModel,
    navigateBack: () -> Unit,
    name: String,
    uri: String?,
    ingredient: String,
    instruction: String,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.add_recipe),
                //fontFamily = FontFamily(Font(R.font.playfair_display_regular)),
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    Icons.Filled.Close,
                    //painterResource(R.drawable.ic_baseline_clear_24),
                    contentDescription = "logo")
            }
        },
        actions = {
            IconButton(onClick = {
                if (uri != null) {
                    if (name.isNotEmpty() && ingredient.isNotEmpty() && instruction.isNotEmpty() && uri.isNotEmpty()){
                        val recipeModel = RecipeModel(imageUri = uri, name = name, time = 30, type = 1, ingredients = mutableListOf(ingredient), instructions = mutableListOf(instruction))
                        viewModel.addRecipe(recipeModel)
                        Log.d("Box", "Recipe inserted.")
                        navigateBack()
                    } else {
                        Log.d("Box", "Recipe not inserted.")
                        navigateBack()
                    }
                }
            }) {
                Icon(
                    Icons.Filled.Check,
                    //painterResource(id = R.drawable.ic_baseline_check_24),
                    contentDescription = "Git")
            }
        }
    )
}