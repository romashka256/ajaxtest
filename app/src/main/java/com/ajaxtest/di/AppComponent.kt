package com.ajaxtest.di

import android.app.Activity
import android.content.Context
import com.ajaxtest.App
import com.core.bottomnav.BottomNavigatorImpl
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContactsFragmentBuilderModule::class,
        ViewModelModule::class, DataModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(applicationContext: Context): Builder

        @BindsInstance
        fun url(@Named("baseUrl") baseUrl: String): Builder

        fun build(): AppComponent
    }

    fun mainActivityComponentFactory(): MainActivitySubcomponent.Factory

    fun inject(app: App)
}