package com.esjliew.recipemaker.module

import com.esjliew.recipemaker.repository.RecipeRepository
import org.koin.dsl.module

val recipeRepositoryModule = module {
    single { RecipeRepository(get()) }
}