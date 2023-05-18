package com.esjliew.recipemaker

import android.app.Application
import com.esjliew.recipemaker.database.ObjectBox
import com.esjliew.recipemaker.module.addModule
import com.esjliew.recipemaker.module.homeModule
import com.esjliew.recipemaker.module.recipeRepositoryModule
import com.esjliew.recipemaker.module.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ObjectBox.init(this)
        startKoin{
            androidContext(this@App)
            modules(
                homeModule,
                addModule,
                viewModule,
                recipeRepositoryModule
            )
        }
    }
}