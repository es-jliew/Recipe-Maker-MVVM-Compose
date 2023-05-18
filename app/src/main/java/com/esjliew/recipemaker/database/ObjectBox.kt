package com.esjliew.recipemaker.database

import android.content.Context
import com.esjliew.recipemaker.data.MyObjectBox
import com.esjliew.recipemaker.data.RecipeModel
import com.esjliew.recipemaker.data.RecipeModel_
import io.objectbox.BoxStore
import io.objectbox.exception.DbException
import io.objectbox.exception.FileCorruptException
import io.objectbox.kotlin.toFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Singleton to keep BoxStore reference and provide current list of Recipes Objects.
 */
object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    /**
     * If building the boxStore failed, contains the thrown error message.
     */
    var dbExceptionMessage: String? = null
        private set

    fun init(context: Context) {
        boxStore = try {
            MyObjectBox.builder()
                .androidContext(context.applicationContext)
                .build()
        } catch (e: DbException) {
            if (e.javaClass == DbException::class.java || e is FileCorruptException) {
                dbExceptionMessage = e.toString()
                return
            } else {
                // Failed to build BoxStore due to developer error.
                throw e
            }
        }
    }
}