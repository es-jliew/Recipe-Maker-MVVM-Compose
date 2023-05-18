package com.esjliew.recipemaker.module

import com.esjliew.recipemaker.ui.presentation.addRecipeScreen.AddRecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addModule = module {
    viewModel { AddRecipeViewModel(get()) }
}