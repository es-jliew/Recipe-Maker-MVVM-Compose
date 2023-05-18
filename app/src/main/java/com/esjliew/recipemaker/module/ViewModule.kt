package com.esjliew.recipemaker.module

import com.esjliew.recipemaker.ui.presentation.viewRecipeScreen.ViewRecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { ViewRecipeViewModel(get()) }
}