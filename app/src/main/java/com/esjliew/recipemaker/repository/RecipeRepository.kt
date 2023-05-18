package com.esjliew.recipemaker.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import com.esjliew.recipemaker.data.RecipeModel
import com.esjliew.recipemaker.database.ObjectBox
import io.objectbox.kotlin.toFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream

class RecipeRepository (private val context: Context) {
    /**
     * Test database with fake data.
     */
    private val recipe1 = RecipeModel(imageUri = "Img1", name = "Name1", time = 30, type = 1, ingredients = mutableListOf("ing1","ing2"), instructions = mutableListOf("ins1","ins2"))
    private val recipe2 = RecipeModel(imageUri = "Img1", name = "Name2", time = 30, type = 1, ingredients = mutableListOf("ing1","ing2"), instructions = mutableListOf("ins1","ins2"))
    private val recipe3 = RecipeModel(imageUri = "Img1", name = "Name3", time = 30, type = 1, ingredients = mutableListOf("ing1","ing2"), instructions = mutableListOf("ins1","ins2"))

    private val recipeBox = ObjectBox.boxStore.boxFor(RecipeModel::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getAllRecipe(): Flow<List<RecipeModel>> = recipeBox.query().build().subscribe().toFlow()

    suspend fun getRecipeById(recipeId: Long): RecipeModel = recipeBox.get(recipeId)

    suspend fun addDemoRecipe()
    {
        recipeBox.put(recipe1, recipe2, recipe3)
    }

    suspend fun addRecipe(recipeModel: RecipeModel)
    {
        //Update imageUri to local storage before persisting to database
        recipeModel.imageUri = recipeModel.imageUri?.let { copyFileByUri(context, it.toUri()) }
        Log.d("FIle", "File saved at ${recipeModel.imageUri}")
        recipeBox.put(recipeModel)
    }
    suspend fun deleteRecipe(recipeModel: RecipeModel)
    {

    }
    suspend fun updateRecipe(recipeModel: RecipeModel)
    {

    }

    private fun copyFileByUri(context: Context, pathFrom: Uri): String {
        val inputStream = context.contentResolver.openInputStream(pathFrom)
        val destFile = File(context.getExternalFilesDir(null), getFileNameByUri(pathFrom).toString())
        val output = FileOutputStream(destFile)
        inputStream?.copyTo(output, 4 * 1024)
        inputStream?.close()
        return destFile.toURI().toString()
    }

    @SuppressLint("Recycle")
    private fun getFileNameByUri(uri: Uri): String? {

        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return if (cursor != null) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } else ""
    }
}