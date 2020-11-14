package com.ajaxtest.di

import android.app.Activity
import android.content.Context
import com.ajaxtest.di.scopes.ActivityScope
import com.ajaxtest.view.activity.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Named

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@ActivityScope
@Subcomponent(modules = [ContactsFragmentBuilderModule::class, MainActivityModule::class])
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("activityContext") activityContext: Context,
            @BindsInstance activity: Activity
        ): MainActivitySubcomponent
    }
}
