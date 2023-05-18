package com.esjliew.recipemaker.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class RecipeModel(
    @Id
    var id: Long = 0,
    var imageUri: String? = null,
    var name: String? = null,
    var time: Int = 0,
    var type: Int = 0,
    var ingredients: MutableList<String> = mutableListOf(),
    var instructions: MutableList<String> = mutableListOf(),
)