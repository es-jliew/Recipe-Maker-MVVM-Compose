package com.esjliew.recipemaker.module

import com.esjliew.recipemaker.ui.presentation.homeScreen.HomeViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val homeModule = module {
    viewModel { HomeViewModel(get()) }
}
