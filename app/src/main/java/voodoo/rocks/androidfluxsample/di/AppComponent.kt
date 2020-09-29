package voodoo.rocks.androidfluxsample.di

import dagger.Component
import voodoo.rocks.androidfluxsample.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(appActivity: MainActivity)
}