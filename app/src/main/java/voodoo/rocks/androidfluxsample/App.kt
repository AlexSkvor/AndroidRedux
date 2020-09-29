package voodoo.rocks.androidfluxsample

import android.app.Application
import timber.log.Timber
import voodoo.rocks.androidfluxsample.di.AppModule
import voodoo.rocks.androidfluxsample.di.DaggerAppComponent
import voodoo.rocks.androidfluxsample.di.DependencyProvider
import voodoo.rocks.flux.lifecyclecallbacks.ActivityLifecycleLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initDi()
        registerActivityLifecycleCallbacks(ActivityLifecycleLogger)
    }

    private fun initDi() {
        DependencyProvider.get().component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

}