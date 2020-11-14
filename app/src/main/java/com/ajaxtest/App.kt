package com.ajaxtest

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import com.ajaxtest.di.AppComponent
import com.ajaxtest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.core.bottomnav.BottomNavItemData
import com.core.customviews.other.view.OtherItemData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class App : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .url(Constants.url)
            .build()

        appComponent.inject(this)

        Glide.init(applicationContext, GlideBuilder().setLogLevel(Log.DEBUG))
        Glide.get(applicationContext).setMemoryCategory(MemoryCategory.LOW)

    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    companion object {
        lateinit var appComponent: AppComponent
    }
}