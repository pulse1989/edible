package za.co.kernelpanic.edible.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import za.co.kernelpanic.edible.BuildConfig

@HiltAndroidApp
class EdibleApp: Application() {

    companion object {
        private lateinit var instance: EdibleApp

        fun getInstance(): EdibleApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}